package com.saferent.mapper;

import com.saferent.domain.ContactMessage;
import com.saferent.dto.ContactMessageDTO;
import com.saferent.dto.request.ContactMessageRequest;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-08T09:58:39+0200",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class ContactMessageMapperImpl implements ContactMessageMapper {

    @Override
    public ContactMessageDTO contactMessageToDTO(ContactMessage contactMessage) {
        if ( contactMessage == null ) {
            return null;
        }

        ContactMessageDTO contactMessageDTO = new ContactMessageDTO();

        contactMessageDTO.setId( contactMessage.getId() );
        contactMessageDTO.setName( contactMessage.getName() );
        contactMessageDTO.setSubject( contactMessage.getSubject() );
        contactMessageDTO.setBody( contactMessage.getBody() );
        contactMessageDTO.setEmail( contactMessage.getEmail() );

        return contactMessageDTO;
    }

    @Override
    public ContactMessage contactMessageRequestToContactMessage(ContactMessageRequest contactMessageRequest) {
        if ( contactMessageRequest == null ) {
            return null;
        }

        ContactMessage contactMessage = new ContactMessage();

        contactMessage.setName( contactMessageRequest.getName() );
        contactMessage.setSubject( contactMessageRequest.getSubject() );
        contactMessage.setBody( contactMessageRequest.getBody() );
        contactMessage.setEmail( contactMessageRequest.getEmail() );

        return contactMessage;
    }

    @Override
    public List<ContactMessageDTO> map(List<ContactMessage> contactMessageList) {
        if ( contactMessageList == null ) {
            return null;
        }

        List<ContactMessageDTO> list = new ArrayList<ContactMessageDTO>( contactMessageList.size() );
        for ( ContactMessage contactMessage : contactMessageList ) {
            list.add( contactMessageToDTO( contactMessage ) );
        }

        return list;
    }
}
