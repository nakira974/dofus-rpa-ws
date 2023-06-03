package coffee.lkh.dofusrpa.models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "dofus_character_class")
public enum DofusCharacterClass {
    @XmlElement(name = "undefined")
    UNDEFINED,
    @XmlElement(name = "ecaflip")
    ECAFLIP,
    @XmlElement(name = "eniripsa")
    ENIRIPSA,
    @XmlElement(name = "iop")
    IOP,
    @XmlElement(name = "cra")
    CRA,
    @XmlElement(name = "feca")
    FECA,
    @XmlElement(name = "sacrier")
    SACRIER,
    @XmlElement(name = "sadida")
    SADIDA,
    @XmlElement(name = "osamodas")
    OSAMODAS,
    @XmlElement(name = "enutrof")
    ENUTROF,
    @XmlElement(name = "sram")
    SRAM,
    @XmlElement(name = "xelor")
    XELOR,
    @XmlElement(name = "pandawa")
    PANDAWA,
    @XmlElement(name = "rogue_ruse")
    ROGUE_RUSE,
    @XmlElement(name = "masqueraider_mystique")
    MASQUERAIDER_MYSTIQUE,
    @XmlElement(name = "foggernauts_steam")
    FOGGERNAUTS_STEAM,
    @XmlElement(name = "eliotrope")
    ELIOTROPE,
    @XmlElement(name = "huppermage")
    HUPPERMAGE,
    @XmlElement(name = "ouginak")
    OUGINAK,
    @XmlElement(name = "forgelance_heritage")
    FORGELANCE_HERITAGE
}
