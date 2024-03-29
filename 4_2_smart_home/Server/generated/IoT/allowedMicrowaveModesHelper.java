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
 * Helper class for marshaling/unmarshaling allowedMicrowaveModes.
 **/
public final class allowedMicrowaveModesHelper
{
    public static void write(com.zeroc.Ice.OutputStream ostr, MicrowaveMode[] v)
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
                MicrowaveMode.ice_write(ostr, v[i0]);
            }
        }
    }

    public static MicrowaveMode[] read(com.zeroc.Ice.InputStream istr)
    {
        final MicrowaveMode[] v;
        final int len0 = istr.readAndCheckSeqSize(1);
        v = new MicrowaveMode[len0];
        for(int i0 = 0; i0 < len0; i0++)
        {
            v[i0] = MicrowaveMode.ice_read(istr);
        }
        return v;
    }

    public static void write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<MicrowaveMode[]> v)
    {
        if(v != null && v.isPresent())
        {
            write(ostr, tag, v.get());
        }
    }

    public static void write(com.zeroc.Ice.OutputStream ostr, int tag, MicrowaveMode[] v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            allowedMicrowaveModesHelper.write(ostr, v);
            ostr.endSize(pos);
        }
    }

    public static java.util.Optional<MicrowaveMode[]> read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            MicrowaveMode[] v;
            v = allowedMicrowaveModesHelper.read(istr);
            return java.util.Optional.of(v);
        }
        else
        {
            return java.util.Optional.empty();
        }
    }
}
