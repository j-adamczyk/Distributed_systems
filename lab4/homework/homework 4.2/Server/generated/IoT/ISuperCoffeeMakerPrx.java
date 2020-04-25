//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.3
//
// <auto-generated>
//
// Generated from file `IoT.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package IoT;

public interface ISuperCoffeeMakerPrx extends ICoffeeMakerPrx
{
    default Coffee[] makeCustomCoffeeOrder(SingleCoffeeOrder[] order)
        throws UnsupportedCoffeeTypeException,
               ValueOutOfRangeException
    {
        return makeCustomCoffeeOrder(order, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default Coffee[] makeCustomCoffeeOrder(SingleCoffeeOrder[] order, java.util.Map<String, String> context)
        throws UnsupportedCoffeeTypeException,
               ValueOutOfRangeException
    {
        try
        {
            return _iceI_makeCustomCoffeeOrderAsync(order, context, true).waitForResponseOrUserEx();
        }
        catch(UnsupportedCoffeeTypeException ex)
        {
            throw ex;
        }
        catch(ValueOutOfRangeException ex)
        {
            throw ex;
        }
        catch(com.zeroc.Ice.UserException ex)
        {
            throw new com.zeroc.Ice.UnknownUserException(ex.ice_id(), ex);
        }
    }

    default java.util.concurrent.CompletableFuture<Coffee[]> makeCustomCoffeeOrderAsync(SingleCoffeeOrder[] order)
    {
        return _iceI_makeCustomCoffeeOrderAsync(order, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<Coffee[]> makeCustomCoffeeOrderAsync(SingleCoffeeOrder[] order, java.util.Map<String, String> context)
    {
        return _iceI_makeCustomCoffeeOrderAsync(order, context, false);
    }

    /**
     * @hidden
     * @param iceP_order -
     * @param context -
     * @param sync -
     * @return -
     **/
    default com.zeroc.IceInternal.OutgoingAsync<Coffee[]> _iceI_makeCustomCoffeeOrderAsync(SingleCoffeeOrder[] iceP_order, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<Coffee[]> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "makeCustomCoffeeOrder", com.zeroc.Ice.OperationMode.Idempotent, sync, _iceE_makeCustomCoffeeOrder);
        f.invoke(true, context, null, ostr -> {
                     CoffeeOrderHelper.write(ostr, iceP_order);
                 }, istr -> {
                     Coffee[] ret;
                     ret = CoffeesHelper.read(istr);
                     return ret;
                 });
        return f;
    }

    /** @hidden */
    static final Class<?>[] _iceE_makeCustomCoffeeOrder =
    {
        UnsupportedCoffeeTypeException.class,
        ValueOutOfRangeException.class
    };

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static ISuperCoffeeMakerPrx checkedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, ice_staticId(), ISuperCoffeeMakerPrx.class, _ISuperCoffeeMakerPrxI.class);
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static ISuperCoffeeMakerPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, context, ice_staticId(), ISuperCoffeeMakerPrx.class, _ISuperCoffeeMakerPrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static ISuperCoffeeMakerPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, ice_staticId(), ISuperCoffeeMakerPrx.class, _ISuperCoffeeMakerPrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static ISuperCoffeeMakerPrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, context, ice_staticId(), ISuperCoffeeMakerPrx.class, _ISuperCoffeeMakerPrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @return A proxy for this type.
     **/
    static ISuperCoffeeMakerPrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, ISuperCoffeeMakerPrx.class, _ISuperCoffeeMakerPrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type.
     **/
    static ISuperCoffeeMakerPrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, facet, ISuperCoffeeMakerPrx.class, _ISuperCoffeeMakerPrxI.class);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the per-proxy context.
     * @param newContext The context for the new proxy.
     * @return A proxy with the specified per-proxy context.
     **/
    @Override
    default ISuperCoffeeMakerPrx ice_context(java.util.Map<String, String> newContext)
    {
        return (ISuperCoffeeMakerPrx)_ice_context(newContext);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the adapter ID.
     * @param newAdapterId The adapter ID for the new proxy.
     * @return A proxy with the specified adapter ID.
     **/
    @Override
    default ISuperCoffeeMakerPrx ice_adapterId(String newAdapterId)
    {
        return (ISuperCoffeeMakerPrx)_ice_adapterId(newAdapterId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoints.
     * @param newEndpoints The endpoints for the new proxy.
     * @return A proxy with the specified endpoints.
     **/
    @Override
    default ISuperCoffeeMakerPrx ice_endpoints(com.zeroc.Ice.Endpoint[] newEndpoints)
    {
        return (ISuperCoffeeMakerPrx)_ice_endpoints(newEndpoints);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator cache timeout.
     * @param newTimeout The new locator cache timeout (in seconds).
     * @return A proxy with the specified locator cache timeout.
     **/
    @Override
    default ISuperCoffeeMakerPrx ice_locatorCacheTimeout(int newTimeout)
    {
        return (ISuperCoffeeMakerPrx)_ice_locatorCacheTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the invocation timeout.
     * @param newTimeout The new invocation timeout (in seconds).
     * @return A proxy with the specified invocation timeout.
     **/
    @Override
    default ISuperCoffeeMakerPrx ice_invocationTimeout(int newTimeout)
    {
        return (ISuperCoffeeMakerPrx)_ice_invocationTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for connection caching.
     * @param newCache <code>true</code> if the new proxy should cache connections; <code>false</code> otherwise.
     * @return A proxy with the specified caching policy.
     **/
    @Override
    default ISuperCoffeeMakerPrx ice_connectionCached(boolean newCache)
    {
        return (ISuperCoffeeMakerPrx)_ice_connectionCached(newCache);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoint selection policy.
     * @param newType The new endpoint selection policy.
     * @return A proxy with the specified endpoint selection policy.
     **/
    @Override
    default ISuperCoffeeMakerPrx ice_endpointSelection(com.zeroc.Ice.EndpointSelectionType newType)
    {
        return (ISuperCoffeeMakerPrx)_ice_endpointSelection(newType);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for how it selects endpoints.
     * @param b If <code>b</code> is <code>true</code>, only endpoints that use a secure transport are
     * used by the new proxy. If <code>b</code> is false, the returned proxy uses both secure and
     * insecure endpoints.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default ISuperCoffeeMakerPrx ice_secure(boolean b)
    {
        return (ISuperCoffeeMakerPrx)_ice_secure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the encoding used to marshal parameters.
     * @param e The encoding version to use to marshal request parameters.
     * @return A proxy with the specified encoding version.
     **/
    @Override
    default ISuperCoffeeMakerPrx ice_encodingVersion(com.zeroc.Ice.EncodingVersion e)
    {
        return (ISuperCoffeeMakerPrx)_ice_encodingVersion(e);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its endpoint selection policy.
     * @param b If <code>b</code> is <code>true</code>, the new proxy will use secure endpoints for invocations
     * and only use insecure endpoints if an invocation cannot be made via secure endpoints. If <code>b</code> is
     * <code>false</code>, the proxy prefers insecure endpoints to secure ones.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default ISuperCoffeeMakerPrx ice_preferSecure(boolean b)
    {
        return (ISuperCoffeeMakerPrx)_ice_preferSecure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the router.
     * @param router The router for the new proxy.
     * @return A proxy with the specified router.
     **/
    @Override
    default ISuperCoffeeMakerPrx ice_router(com.zeroc.Ice.RouterPrx router)
    {
        return (ISuperCoffeeMakerPrx)_ice_router(router);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator.
     * @param locator The locator for the new proxy.
     * @return A proxy with the specified locator.
     **/
    @Override
    default ISuperCoffeeMakerPrx ice_locator(com.zeroc.Ice.LocatorPrx locator)
    {
        return (ISuperCoffeeMakerPrx)_ice_locator(locator);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for collocation optimization.
     * @param b <code>true</code> if the new proxy enables collocation optimization; <code>false</code> otherwise.
     * @return A proxy with the specified collocation optimization.
     **/
    @Override
    default ISuperCoffeeMakerPrx ice_collocationOptimized(boolean b)
    {
        return (ISuperCoffeeMakerPrx)_ice_collocationOptimized(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses twoway invocations.
     * @return A proxy that uses twoway invocations.
     **/
    @Override
    default ISuperCoffeeMakerPrx ice_twoway()
    {
        return (ISuperCoffeeMakerPrx)_ice_twoway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses oneway invocations.
     * @return A proxy that uses oneway invocations.
     **/
    @Override
    default ISuperCoffeeMakerPrx ice_oneway()
    {
        return (ISuperCoffeeMakerPrx)_ice_oneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch oneway invocations.
     * @return A proxy that uses batch oneway invocations.
     **/
    @Override
    default ISuperCoffeeMakerPrx ice_batchOneway()
    {
        return (ISuperCoffeeMakerPrx)_ice_batchOneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses datagram invocations.
     * @return A proxy that uses datagram invocations.
     **/
    @Override
    default ISuperCoffeeMakerPrx ice_datagram()
    {
        return (ISuperCoffeeMakerPrx)_ice_datagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch datagram invocations.
     * @return A proxy that uses batch datagram invocations.
     **/
    @Override
    default ISuperCoffeeMakerPrx ice_batchDatagram()
    {
        return (ISuperCoffeeMakerPrx)_ice_batchDatagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, except for compression.
     * @param co <code>true</code> enables compression for the new proxy; <code>false</code> disables compression.
     * @return A proxy with the specified compression setting.
     **/
    @Override
    default ISuperCoffeeMakerPrx ice_compress(boolean co)
    {
        return (ISuperCoffeeMakerPrx)_ice_compress(co);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection timeout setting.
     * @param t The connection timeout for the proxy in milliseconds.
     * @return A proxy with the specified timeout.
     **/
    @Override
    default ISuperCoffeeMakerPrx ice_timeout(int t)
    {
        return (ISuperCoffeeMakerPrx)_ice_timeout(t);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection ID.
     * @param connectionId The connection ID for the new proxy. An empty string removes the connection ID.
     * @return A proxy with the specified connection ID.
     **/
    @Override
    default ISuperCoffeeMakerPrx ice_connectionId(String connectionId)
    {
        return (ISuperCoffeeMakerPrx)_ice_connectionId(connectionId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except it's a fixed proxy bound
     * the given connection.@param connection The fixed proxy connection.
     * @return A fixed proxy bound to the given connection.
     **/
    @Override
    default ISuperCoffeeMakerPrx ice_fixed(com.zeroc.Ice.Connection connection)
    {
        return (ISuperCoffeeMakerPrx)_ice_fixed(connection);
    }

    static String ice_staticId()
    {
        return "::IoT::ISuperCoffeeMaker";
    }
}