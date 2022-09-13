package com.sdconecta.backendtest.models;


import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    public static Specification<UserModel> hasNameILike(String name) {
        return (root, query, criteriaBuilder) ->
          criteriaBuilder.like(criteriaBuilder.lower(root.<String>get("name")), "%" + name.toLowerCase() + "%");
    }
    public static Specification<UserModel> hasSurnameILike(String name) {
        return (root, query, criteriaBuilder) ->
          criteriaBuilder.like(criteriaBuilder.lower(root.<String>get("surname")), "%" + name.toLowerCase() + "%");
    }

   /*  public static Specification<UserModel> hasSpecialty(String specialty) {
        return (root, query, criteriaBuilder) -> {
            Join<UserModel, CrmModel> userSpecialty = root.join("crms");
            var p1 = criteriaBuilder.like(criteriaBuilder.lower(userSpecialty.get("specialty")), "%" + specialty.toLowerCase() + "%");
            
            return criteriaBuilder.exists(); 
        };
    } */

    
}
