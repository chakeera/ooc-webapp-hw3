package Servlet;

import Service.SecurityService;

public interface Routable {

    String getMapping();

    void setSecurityService(SecurityService securityService);
}