package si.ape.staff.models.converters;

import si.ape.staff.models.entities.BranchEntity;
import si.ape.staff.lib.Branch;

public class BranchConverter {

    public static Branch toDto(BranchEntity entity) {

        Branch dto = new Branch();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setBranchType(BranchTypeConverter.toDto(entity.getBranchType()));
        dto.setStreet(StreetConverter.toDto(entity.getStreet()));
        return dto;

    }

    public static BranchEntity toEntity(Branch dto) {

        BranchEntity entity = new BranchEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setBranchType(BranchTypeConverter.toEntity(dto.getBranchType()));
        entity.setStreet(StreetConverter.toEntity(dto.getStreet()));
        return entity;

    }

}
