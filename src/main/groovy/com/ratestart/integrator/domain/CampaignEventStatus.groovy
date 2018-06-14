package com.ratestart.integrator.domain

enum CampaignEventStatus {
    NEW(1, "new"),
    APPROVED(2, "name"),
    RUNNING(3, "running"),
    STOPPED(4, "stopped"),
    DELETED(5, "deleted"),
    PROVISIONED(6, "provisioned"),
    HOLDING(7, "holding"),
    USER_STOP_REQUESTED(8, "user stop requested"),
    REPROVISION(9, "reprovision"),
    ENDED(10,"ended"),
    INITIALIZED(17, "initialized"),
    AUTO_INIT_REQUESTED(16, "auto init requested"),
    AUTO_INIT_WORKING(25, "auto init working"),
    AUTO_INIT_FAILED(26, "auto init failed"),
    AUTO_PROVISION_REQUESTED(18, "auto provision requested"),
    AUTO_PROVISION_WORKING(20, "auto provision working"),
    AUTO_PROVISION_FAILED(21, "auto provision failed"),
    PROVISION_AUTO_START_REQUESTED(27, "provision: auto start requested"),
    AUTO_START_WORKING(29, "auto start working"),
    STOPPED_AUTO_START_FAILED(31, "stopped: auto start failed"),
    RUNNING_AUTO_PAUSE_REQUESTED(32, "running: auto pause requested"),
    RUNNING_AUTO_PAUSE_FAILED(34, "running: auto pause failed"),
    USER_AUTO_PAUSE_REQUESTED(47, "usr: auto pause requested"),
    AUTO_PAUSE_WORKING(33, "auto pause working"),
    RUNNING_AUTO_END_REQUESTED(38,"running: auto end requested"),
    STOPPED_AUTO_END_REQUESTED(39,"stopped: auto end requested"),
    USER_AUTO_PAUSE_FAILED(48, "usr: auto pause failed"),
    AUTORUN_REQUESTED(22, "autorun requested"),
    AUTORUN_WORKING(23, "autorun working"),
    AUTORUN_FAILED(24, "autorun failed"),
    AUTO_END_WORKING(40, "auto end working"), //','Background stop process has started'
    RUNNING_AUTO_END_FAILED(41, "running: auto end failed"),//,'Background stop process has failed'
    STOPPED_AUTO_END_FAILED(42,"stopped: auto end failed"),//'Background stop process has failed'
    USER_AUTO_END_REQUESTED(45, "usr: auto end requested"),
    USER_AUTO_END_FAILED(46,"usr: auto end failed"),//'Background end  process has failed'
    INACTIVE(52, "inactive"),
    ERROR(19, "error")

    Long statusCode
    String eventName

    CampaignEventStatus(Long statusCode, String eventName){
        this.statusCode = statusCode
        this.eventName = eventName
    }

    static CampaignEventStatus fromStatusCode(Long statusCode){
        values().find{it.statusCode == statusCode}
    }

    static CampaignEventStatus fromEventName(String eventName){
        values().find{it.eventName.equalsIgnoreCase(eventName)}
    }
}
