package coffee.lkh.robotws.models.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Date;

@XmlRootElement(name = "npcap_packet_dto")
@XmlAccessorType(XmlAccessType.FIELD)
public class NpcapPacketDto {

    @XmlElement(name = "timestamp")
    private Date _timestamp;

    @XmlElement(name = "data")
    private byte[] _data;

    // Default constructor required by JAXB
    public NpcapPacketDto() {}

    public NpcapPacketDto(Date timestamp, byte[] data) {
        this._timestamp = timestamp;
        this._data = data;
    }

    public Date getTimestamp() {
        return _timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this._timestamp = timestamp;
    }

    public byte[] getData() {
        return _data;
    }

    public void setData(byte[] data) {
        this._data = data;
    }
}