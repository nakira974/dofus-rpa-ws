package coffee.lkh.dofusrpa.webservices.implemantations;

import coffee.lkh.dofusrpa.models.datasource.InputStreamDataSource;
import coffee.lkh.dofusrpa.webservices.IFileTransferService;
import jakarta.activation.DataHandler;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlMimeType;
import jakarta.xml.soap.*;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;


@WebService(endpointInterface = "coffee.lkh.dofusrpa.webservices.IFileTransferService",
        serviceName = "FileTransferService")
@BindingType(SOAPBinding.SOAP12HTTP_MTOM_BINDING)
public class FileTransferService implements IFileTransferService {

    @WebMethod
    public SOAPMessage sendFile(@XmlMimeType("application/octet-stream") byte @NotNull [] remoteOctetStream) throws SOAPException {
        // Convert ByteBuffer to InputStream
        final AtomicReference<InputStream> inputStream = new AtomicReference<>(new ByteArrayInputStream(remoteOctetStream));

        // Create DataHandler from InputStream
        final DataHandler dataHandler = new DataHandler(new InputStreamDataSource(inputStream.get()));

        // Create SOAP Message with attachment
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // Add attachment to SOAP Message
        AttachmentPart attachment = soapMessage.createAttachmentPart(dataHandler);
        attachment.setContentId("file_attachment");
        soapMessage.addAttachmentPart(attachment);

        return soapMessage;
    }
}



