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

/**
 * Helper class for marshaling/unmarshaling CoffeeOrder.
 **/
public final class CoffeeOrderHelper
{
    public static void write(com.zeroc.Ice.OutputStream ostr, SingleCoffeeOrder[] v)
    {
        if(v == null)
        {
            ostr.writeSize(0);
        }
        else
        {
            ostr.writeSize(v.length);
            for(int i0 = 0; i0 < v.length; i0++)
            {
                SingleCoffeeOrder.ice_write(ostr, v[i0]);
            }
        }
    }

    public static SingleCoffeeOrder[] read(com.zeroc.Ice.InputStream istr)
    {
        final SingleCoffeeOrder[] v;
        final int len0 = istr.readAndCheckSeqSize(5);
        v = new SingleCoffeeOrder[len0];
        for(int i0 = 0; i0 < len0; i0++)
        {
            v[i0] = SingleCoffeeOrder.ice_read(istr);
        }
        return v;
    }

    public static void write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<SingleCoffeeOrder[]> v)
    {
        if(v != null && v.isPresent())
        {
            write(ostr, tag, v.get());
        }
    }

    public static void write(com.zeroc.Ice.OutputStream ostr, int tag, SingleCoffeeOrder[] v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            CoffeeOrderHelper.write(ostr, v);
            ostr.endSize(pos);
        }
    }

    public static java.util.Optional<SingleCoffeeOrder[]> read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            SingleCoffeeOrder[] v;
            v = CoffeeOrderHelper.read(istr);
            return java.util.Optional.of(v);
        }
        else
        {
            return java.util.Optional.empty();
        }
    }
}
