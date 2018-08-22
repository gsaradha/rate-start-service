
package com.ratestart.integrator.repo

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface SubscriptionAlertRepository extends CrudRepository<SubscriptionAlert, Long> {

    @Query(value="""\
			    select distinct sa.device_token, sa.idSubscriptionAlert, sa.Alert_idAlert_FK, sa.device_type  
                from SubscriptionAlert sa join Alert alert on sa.Alert_idAlert_FK=alert.idAlert
                where alert.idAlert = :alertId or sa.Alert_idAlert_FK=6
            """, nativeQuery = true)
    List<SubscriptionAlert> fetchAllUserAlerts(@Param("alertId") Integer alertId)

    @Query(value="""\
			    select sa.idSubscriptionAlert
                from SubscriptionAlert sa join Alert alert on sa.Alert_idAlert_FK=alert.idAlert
                where alert.idAlert = :alertId and sa.device_token= :deviceToken
            """, nativeQuery = true)
    Integer findExistingAlert(@Param("alertId") Long alertId, @Param("deviceToken") String deviceToken)

    @Query(value="""\
			    select sa.device_token, sa.idSubscriptionAlert, sa.Alert_idAlert_FK from SubscriptionAlert sa 
			    where sa.device_token= :deviceToken
            """, nativeQuery = true)
    List<SubscriptionAlert> fetchAlertsByDeviceToken(@Param("deviceToken") String deviceToken)

}
