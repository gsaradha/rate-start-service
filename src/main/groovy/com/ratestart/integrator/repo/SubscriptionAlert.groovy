package com.ratestart.integrator.repo

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

@Entity
@Table(name = "SubscriptionAlert")
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode
class SubscriptionAlert implements Serializable {
    private static final long serialVersionUID = 1L

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idSubscriptionAlert")
    Long idSubscriptionAlert

    @Column(name = "device_token")
    String deviceToken

    @Column(name = "device_type")
    String deviceType

    @Column(name = "Alert_idAlert_FK")
    Long idAlert

}
