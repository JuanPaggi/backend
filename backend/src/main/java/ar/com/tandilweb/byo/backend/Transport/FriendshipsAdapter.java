package ar.com.tandilweb.byo.backend.Transport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import ar.com.tandilweb.byo.backend.Gateway.fcm.FCMNotify;
import ar.com.tandilweb.byo.backend.Gateway.fcm.FCMPayloadData;
import ar.com.tandilweb.byo.backend.Gateway.fcm.FirebaseCloudMessaging;
import ar.com.tandilweb.byo.backend.Gateway.fcm.HttpFCMPayload;
import ar.com.tandilweb.byo.backend.Model.domain.Friendships;
import ar.com.tandilweb.byo.backend.Model.domain.Users;
import ar.com.tandilweb.byo.backend.Model.repository.FriendshipsRepository;
import ar.com.tandilweb.byo.backend.Model.repository.UserRepository;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.Notification;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.Notification.Types;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.ResponseDTO.Code;
import ar.com.tandilweb.byo.backend.Presentation.dto.out.VCard;

public class FriendshipsAdapter {

	@Value("${fcm.serverkey}")
	private String serverKey;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FriendshipsRepository friendShipRepository;

	@Autowired
	private FirebaseCloudMessaging firebaseCloudMessaging;
	
	@Autowired
	private UserAdapter userAdapter;

	public ResponseDTO validateFriendshipRequest(long idreq, long idtar) {
		ResponseDTO out = new ResponseDTO();
		Users requester = userRepository.findById(idreq);
		Users target = userRepository.findById(idtar);
		if (requester != null && target != null) {
			Friendships fs = new Friendships();
			fs.setDate_emitted(new Date());
			fs.setId_user_requester(idreq);
			fs.setId_user_target(idtar);
			Friendships record = friendShipRepository.create(fs);
			if (record != null) {
				// enviar notificacion
				HttpFCMPayload payload = new HttpFCMPayload();
				firebaseCloudMessaging.setServerKey(serverKey);
				List<String> rids = new ArrayList<String>();
				rids.add(target.getFcmToken());
				//payload.setRegistration_ids(rids);
				// payload.setTopic('generalTopic');
				FCMNotify notificacion = new FCMNotify("Solicitud de contacto",
						"Recibiste una nueva solicitud de contacto de " + requester.getFirst_name() + " "
								+ requester.getLast_name());
				payload.setNotification(notificacion);
				payload.setTarget(target.getFcmToken());
				System.out.println(payload.getBody());
				firebaseCloudMessaging.send(payload);

				out.code = Code.CREATED;
				out.description = "Asociación exitosa";
			} else {
				out.code = Code.INTERNAL_SERVER_ERROR;
				out.description = "No se pudo crear el registro";
			}
		} else {
			out.code = Code.BAD_REQUEST;
			out.description = "Uno de los usuarios no existe";
		}
		return out;
	}

	public List<Notification> getRequestSendedReceived(Users me) {
		List<Notification> notifys = new ArrayList<Notification>();
		List<Friendships> fss = friendShipRepository.getRequestsSendedReceivedBy(me.getId_user());
		if(fss != null)
			for(Friendships fs : fss) {
				Notification n = new Notification();
				if(fs.getId_user_requester() == me.getId_user()) {
					n.tipo = Types.SOLICITUD_ENVIADA;
					n.userTarget = userAdapter.getVCardByUser(fs.getId_user_target());
				} else {
					n.tipo = Types.SOLICITUD_RECIBIDA;
					n.userTarget = userAdapter.getVCardByUser(fs.getId_user_requester());
				}
				notifys.add(n);
			}
		return notifys;
	}
	
	public ResponseDTO validateFriendAcceptance(long idRequester, long idTarget) {
		ResponseDTO out = new ResponseDTO();
		//el get(0) no me gusta mucho
		Friendships friendship = friendShipRepository.getFriendship(idRequester, idTarget).get(0);
		if (friendship != null) {
			friendship.setIs_accepted(true);
			friendShipRepository.update(friendship);
			out.code = Code.OK;
			out.description = "Solicitud aceptada";
		} else {
			out.code = Code.BAD_REQUEST;
			out.description = "Uno de los usuarios no existe";
		}
		return out;
	}
	
	public ResponseDTO cancelFriendRequest(long idRequester, long idTarget) {
		ResponseDTO out = new ResponseDTO();
		try {
			friendShipRepository.delete(idRequester, idTarget);
			out.code = Code.OK;
			out.description = "Solicitud cancelada";
		} catch (Exception e) {
			out.code = Code.BAD_REQUEST;
			out.description = "No existe peticion de amistad";
		}
		return out;
	}
	
	public ResponseDTO getLastNotificationsNumber(long idTarget) {
		ResponseDTO out = new ResponseDTO();
		try {
			int notifications = friendShipRepository.getLastNotificationsNumber(idTarget);
			out.code = Code.OK;
			out.description = String.valueOf(notifications);
		} catch (Exception e) {
			out.code = Code.BAD_REQUEST;
			out.description = "El usuario no existe";
		}
		return out;
	}
	
	public ResponseDTO markRequestNotificationsAsViewed(long idUser) {
		ResponseDTO out = new ResponseDTO();
		try {
			friendShipRepository.markRequestNotificationsAsViewed(idUser);
			out.code = Code.OK;
			out.description = "Notificaciones Vistas";
		} catch (Exception e) {
			out.code = Code.BAD_REQUEST;
			out.description = "Este usuario no tiene notificaciones";
		}
		return out;
	}
	
	public List<VCard> getFriends(Users me) {
		
		List<VCard> out = new ArrayList<VCard>();
		
		List<Friendships> fs = friendShipRepository.getFriendsAccepted(me.getId_user());
		
		if(fs!=null)
			for(Friendships f: fs) {
				VCard vcard;
				if(f.getId_user_requester() == me.getId_user())
					vcard = userAdapter.getVCardByUser(f.getId_user_target());
				else
					vcard = userAdapter.getVCardByUser(f.getId_user_requester());
				out.add(vcard);
			}
		
		return out;
	}

}
