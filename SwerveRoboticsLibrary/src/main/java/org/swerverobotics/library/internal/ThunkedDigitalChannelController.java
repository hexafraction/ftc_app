package org.swerverobotics.library.internal;

import com.qualcomm.robotcore.hardware.DigitalChannelController;
import com.qualcomm.robotcore.util.SerialNumber;

/**
 * Another in our series of Thunked objects
 */
public class ThunkedDigitalChannelController implements DigitalChannelController
    {
    //----------------------------------------------------------------------------------------------
    // State
    //----------------------------------------------------------------------------------------------

    public DigitalChannelController target;          // can only talk to him on the loop thread

    //----------------------------------------------------------------------------------------------
    // Construction
    //----------------------------------------------------------------------------------------------

    private ThunkedDigitalChannelController(DigitalChannelController target)
        {
        if (target == null) throw new NullPointerException("null " + this.getClass().getSimpleName() + " target");
        this.target = target;
        }

    static public ThunkedDigitalChannelController create(DigitalChannelController target)
        {
        return target instanceof ThunkedDigitalChannelController ? (ThunkedDigitalChannelController)target : new ThunkedDigitalChannelController(target);
        }

    //----------------------------------------------------------------------------------------------
    // HardwareDevice
    //----------------------------------------------------------------------------------------------

    @Override public void close()
        {
        (new ThunkForWriting()
            {
            @Override protected void actionOnLoopThread()
                {
                target.close();
                }
            }).doWriteOperation();
        }

    @Override public int getVersion()
        {
        return (new ThunkForReading<Integer>()
            {
            @Override protected void actionOnLoopThread()
                {
                this.result = target.getVersion();
                }
            }).doReadOperation();
        }

    @Override public String getConnectionInfo()
        {
        return (new ThunkForReading<String>()
            {
            @Override protected void actionOnLoopThread()
                {
                this.result = target.getConnectionInfo();
                }
            }).doReadOperation();
        }

    @Override public String getDeviceName()
        {
        return (new ThunkForReading<String>()
            {
            @Override protected void actionOnLoopThread()
                {
                this.result = target.getDeviceName();
                }
            }).doReadOperation();
        }

    //----------------------------------------------------------------------------------------------
    // DigitalChannelController
    //----------------------------------------------------------------------------------------------

    @Override public SerialNumber getSerialNumber()
        {
        return (new ThunkForReading<SerialNumber>()
            {
            @Override protected void actionOnLoopThread()
                {
                this.result = target.getSerialNumber();
                }
            }).doReadOperation();
        }
    
    @Override public DigitalChannelController.Mode getDigitalChannelMode(final int channel)
        {
        return (new ThunkForReading<Mode>()
            {
            @Override protected void actionOnLoopThread()
                {
                this.result = target.getDigitalChannelMode(channel);
                }
            }).doReadOperation();
        }

    @Override public void setDigitalChannelMode(final int channel, final DigitalChannelController.Mode mode)
        {
        (new ThunkForWriting()
            {
            @Override protected void actionOnLoopThread()
                {
                target.setDigitalChannelMode(channel, mode);
                }
            }).doWriteOperation();
        }
        
    @Override public boolean getDigitalChannelState(final int channel)
        {
        return (new ThunkForReading<Boolean>()
            {
            @Override protected void actionOnLoopThread()
                {
                this.result = target.getDigitalChannelState(channel);
                }
            }).doReadOperation();
        }

    @Override public void setDigitalChannelState(final int channel, final boolean state)
        {
        (new ThunkForWriting()
            {
            @Override protected void actionOnLoopThread()
                {
                target.setDigitalChannelState(channel, state);
                }
            }).doWriteOperation();
        }
    
    }