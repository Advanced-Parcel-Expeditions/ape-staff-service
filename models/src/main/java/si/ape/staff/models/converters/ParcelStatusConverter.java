package si.ape.staff.models.converters;

import si.ape.staff.lib.ParcelStatus;
import si.ape.staff.models.entities.ParcelStatusEntity;

public class ParcelStatusConverter {

    public static ParcelStatus toDto(ParcelStatusEntity entity) {

        ParcelStatus dto = new ParcelStatus();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;

    }

    public static ParcelStatusEntity toEntity(ParcelStatus dto) {

        ParcelStatusEntity entity = new ParcelStatusEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;

    }

}




