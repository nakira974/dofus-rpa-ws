package coffee.lkh.robotws.models.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jnetpcap.PcapIf;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

@XmlRootElement(name = "pcap_header_dto")
@XmlAccessorType(XmlAccessType.FIELD)
public class PcapHeaderDto {

    public static final int SIZE_BYTES = 24;

    @XmlElement(name = "major_version")
    private final short _majorVersion;

    @XmlElement(name = "minor_version")
    private final short _minorVersion;

    @XmlElement(name = "timezone_offset_seconds")
    private final int _timezoneOffsetSeconds;

    @XmlElement(name = "timezone_offset_micros")
    private final int _timezoneOffsetMicros;

    @XmlElement(name = "snapshot_length")
    private final int _snapshotLength;

    @XmlElement(name = "link_type")
    private final int _linkType;

    public PcapHeaderDto(FileChannel channel, PcapIf device) throws Exception {
        this._majorVersion = 2; // PCAP version 2.x
        this._minorVersion = 4; // PCAP version 2.4
        this._timezoneOffsetSeconds = 0; // GMT
        this._timezoneOffsetMicros = 0; // GMT
        this._snapshotLength = computeSnapshotLength(device);
        this._linkType = computeLinkType(channel);
    }

    public void writeTo(@NotNull ByteBuffer buffer) {
        buffer.putShort(_majorVersion);
        buffer.putShort(_minorVersion);
        buffer.putInt(_timezoneOffsetSeconds);
        buffer.putInt(_timezoneOffsetMicros);
        buffer.putInt(_snapshotLength);
        buffer.putInt(_linkType);
    }

    @Contract("_ -> new")
    private static @NotNull Integer computeSnapshotLength(@NotNull PcapIf device) throws Exception {
        // Compute the snapshot length as the minimum of the maximum packet size and 65535 (the maximum allowed by libpcap)
        return Math.min(device.getHardwareAddress().length + 14, 65535);
    }

    @Contract("_ -> new")
    private static @NotNull Integer computeLinkType(@NotNull FileChannel channel) throws Exception {
        ByteBuffer headerBuffer = ByteBuffer.allocateDirect(SIZE_BYTES).order(
                ByteOrder.nativeOrder());
        channel.read(headerBuffer);
        headerBuffer.flip();
        return (int) headerBuffer.getShort(20);
    }
}
