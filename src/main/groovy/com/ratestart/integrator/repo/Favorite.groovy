package com.ratestart.integrator.repo

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(name = "Favorites")
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode
class Favorite implements Serializable {
    private static final long serialVersionUID = 1L

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idFavorites")
    Long idFavorites

    @Column(name = "User_idUser_FK")
    Long idUser

    @Column(name = "Tip_idTip_FK")
    Long idTip

    @Column(name = "tip", insertable = false, updatable = false)
    String tip

    @Column(name = "Category_idCategory_FK")
    Long idCategory

}
