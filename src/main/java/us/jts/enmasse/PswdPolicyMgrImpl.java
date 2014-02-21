/*
 * Copyright (c) 2009-2014, JoshuaTree. All Rights Reserved.
 */
package us.jts.enmasse;

import us.jts.fortress.PwPolicyMgr;
import us.jts.fortress.PwPolicyMgrFactory;
import us.jts.fortress.SecurityException;
import us.jts.fortress.rbac.PwPolicy;
import us.jts.fortress.rest.FortRequest;
import us.jts.fortress.rest.FortResponse;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Utility for EnMasse Server.  This class is thread safe.
 *
 * @author Shawn McKinney
 */
class PswdPolicyMgrImpl
{
    private static final String CLS_NM = PswdPolicyMgrImpl.class.getName();
    private static final Logger log = Logger.getLogger(CLS_NM);

    /**
     * ************************************************************************************************************************************
     * BEGIN PSWDPOLICYMGR
     * **************************************************************************************************************************************
     */
    FortResponse addPolicy(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            PwPolicy inPolicy = (PwPolicy) request.getEntity();
            PwPolicyMgr policyMgr = PwPolicyMgrFactory.createInstance(request.getContextId());
            policyMgr.setAdmin(request.getSession());
            policyMgr.add(inPolicy);
            response.setEntity(inPolicy);
            response.setErrorCode(0);
        }
        catch (us.jts.fortress.SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse updatePolicy(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            PwPolicy inPolicy = (PwPolicy) request.getEntity();
            PwPolicyMgr policyMgr = PwPolicyMgrFactory.createInstance(request.getContextId());
            policyMgr.setAdmin(request.getSession());
            policyMgr.update(inPolicy);
            response.setEntity(inPolicy);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse deletePolicy(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            PwPolicy inPolicy = (PwPolicy) request.getEntity();
            PwPolicyMgr policyMgr = PwPolicyMgrFactory.createInstance(request.getContextId());
            policyMgr.setAdmin(request.getSession());
            policyMgr.delete(inPolicy);
            response.setEntity(inPolicy);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse readPolicy(FortRequest request)
    {
        FortResponse response = new FortResponse();
        PwPolicy outPolicy;
        try
        {
            PwPolicy inPolicy = (PwPolicy) request.getEntity();
            PwPolicyMgr policyMgr = PwPolicyMgrFactory.createInstance(request.getContextId());
            policyMgr.setAdmin(request.getSession());
            outPolicy = policyMgr.read(inPolicy.getName());
            response.setEntity(outPolicy);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse searchPolicy(FortRequest request)
    {
        FortResponse response = new FortResponse();
        List<PwPolicy> policyList;
        try
        {
            PwPolicy inPolicy = (PwPolicy) request.getEntity();
            PwPolicyMgr policyMgr = PwPolicyMgrFactory.createInstance(request.getContextId());
            policyMgr.setAdmin(request.getSession());
            policyList = policyMgr.search(inPolicy.getName());
            response.setEntities(policyList);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse updateUserPolicy(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            PwPolicy inPolicy = (PwPolicy) request.getEntity();
            PwPolicyMgr policyMgr = PwPolicyMgrFactory.createInstance(request.getContextId());
            policyMgr.setAdmin(request.getSession());
            String userId = request.getValue();
            policyMgr.updateUserPolicy(userId, inPolicy.getName());
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }

    FortResponse deleteUserPolicy(FortRequest request)
    {
        FortResponse response = new FortResponse();
        try
        {
            PwPolicyMgr policyMgr = PwPolicyMgrFactory.createInstance(request.getContextId());
            policyMgr.setAdmin(request.getSession());
            String userId = request.getValue();
            policyMgr.deletePasswordPolicy(userId);
            response.setErrorCode(0);
        }
        catch (SecurityException se)
        {
            log.info(CLS_NM + " caught " + se);
            response.setErrorCode(se.getErrorId());
            response.setErrorMessage(se.getMessage());
        }
        return response;
    }
}