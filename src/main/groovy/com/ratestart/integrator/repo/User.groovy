package com.ratestart.integrator.repo

import com.ratestart.integrator.domain.BooleanConverter
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(name = "User")
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode
class User implements Serializable {
    private static final long serialVersionUID = 1L

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idUser")
    Long idUser

    @Basic(optional = false)
    @Column(name = "email")
    String email

    @Basic(optional = false)
    @Column(name = "password")
    String password

    @Basic(optional = false)
    @Column(name = "name")
    String name

    @Basic(optional = false)
    @Column(name = "subscribed")
    @Convert(converter = BooleanConverter)
    Boolean hasSubscribed

}
