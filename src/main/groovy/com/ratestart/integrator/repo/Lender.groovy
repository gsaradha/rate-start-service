package com.ratestart.integrator.repo

import com.ratestart.integrator.domain.BooleanConverter
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(name = "Lender")
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode
class Lender implements Serializable {
    private static final long serialVersionUID = 1L

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idLender")
    Long idLender

    @Basic(optional = false)
    @Column(name = "user_name")
    String userName

    @Basic(optional = false)
    @Column(name = "password")
    String password

    @Basic(optional = false)
    @Column(name = "name")
    String name

    @Basic(optional = false)
    @Column(name = "email")
    String email

    @Basic(optional = false)
    @Column(name = "is_mortgage_lender")
    @Convert(converter = BooleanConverter)
    Boolean isMortgageLender;

    @Basic(optional = false)
    @Column(name = "nmls_id")
    String nmlsId

    @Basic(optional = false)
    @Column(name = "state_license")
    String stateLicense

    @Basic(optional = false)
    @Column(name = "phone")
    String phone

    @Column(name = "logo_filename")
    String logoFilename

    @Column(name = "base64_logo")
    String base64Logo

    @Column(name = "is_verified")
    @Convert(converter = BooleanConverter)
    Boolean isVerified

}
