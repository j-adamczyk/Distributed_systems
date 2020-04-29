# -*- coding: utf-8 -*-
#
# Copyright (c) ZeroC, Inc. All rights reserved.
#
#
# Ice version 3.7.3
#
# <auto-generated>
#
# Generated from file `IoT.ice'
#
# Warning: do not edit this file.
#
# </auto-generated>
#

from sys import version_info as _version_info_
import Ice, IcePy

# Start of module IoT
_M_IoT = Ice.openModule('IoT')
__name__ = 'IoT'

if '_t_DeviceData' not in _M_IoT.__dict__:
    _M_IoT._t_DeviceData = IcePy.defineDictionary('::IoT::DeviceData', (), IcePy._t_string, IcePy._t_string)

if '_t_NewSettings' not in _M_IoT.__dict__:
    _M_IoT._t_NewSettings = IcePy.defineDictionary('::IoT::NewSettings', (), IcePy._t_string, IcePy._t_string)

if 'CoffeeType' not in _M_IoT.__dict__:
    _M_IoT.CoffeeType = Ice.createTempClass()
    class CoffeeType(Ice.EnumBase):

        def __init__(self, _n, _v):
            Ice.EnumBase.__init__(self, _n, _v)

        def valueOf(self, _n):
            if _n in self._enumerators:
                return self._enumerators[_n]
            return None
        valueOf = classmethod(valueOf)

    CoffeeType.ESPRESSO = CoffeeType("ESPRESSO", 0)
    CoffeeType.AMERICANO = CoffeeType("AMERICANO", 1)
    CoffeeType.CAPPUCCINO = CoffeeType("CAPPUCCINO", 2)
    CoffeeType.LATTE = CoffeeType("LATTE", 3)
    CoffeeType.HOTWATER = CoffeeType("HOTWATER", 4)
    CoffeeType.COLDWATER = CoffeeType("COLDWATER", 5)
    CoffeeType.HOTMILK = CoffeeType("HOTMILK", 6)
    CoffeeType.COLDMILK = CoffeeType("COLDMILK", 7)
    CoffeeType.COLDCOFFEE = CoffeeType("COLDCOFFEE", 8)
    CoffeeType._enumerators = { 0:CoffeeType.ESPRESSO, 1:CoffeeType.AMERICANO, 2:CoffeeType.CAPPUCCINO, 3:CoffeeType.LATTE, 4:CoffeeType.HOTWATER, 5:CoffeeType.COLDWATER, 6:CoffeeType.HOTMILK, 7:CoffeeType.COLDMILK, 8:CoffeeType.COLDCOFFEE }

    _M_IoT._t_CoffeeType = IcePy.defineEnum('::IoT::CoffeeType', CoffeeType, (), CoffeeType._enumerators)

    _M_IoT.CoffeeType = CoffeeType
    del CoffeeType

if '_t_allowedCoffeeTypes' not in _M_IoT.__dict__:
    _M_IoT._t_allowedCoffeeTypes = IcePy.defineSequence('::IoT::allowedCoffeeTypes', (), _M_IoT._t_CoffeeType)

if 'MicrowaveMode' not in _M_IoT.__dict__:
    _M_IoT.MicrowaveMode = Ice.createTempClass()
    class MicrowaveMode(Ice.EnumBase):

        def __init__(self, _n, _v):
            Ice.EnumBase.__init__(self, _n, _v)

        def valueOf(self, _n):
            if _n in self._enumerators:
                return self._enumerators[_n]
            return None
        valueOf = classmethod(valueOf)

    MicrowaveMode.HEATUP = MicrowaveMode("HEATUP", 0)
    MicrowaveMode.KEEPWARM = MicrowaveMode("KEEPWARM", 1)
    MicrowaveMode.DEFROST = MicrowaveMode("DEFROST", 2)
    MicrowaveMode.GRILL = MicrowaveMode("GRILL", 3)
    MicrowaveMode._enumerators = { 0:MicrowaveMode.HEATUP, 1:MicrowaveMode.KEEPWARM, 2:MicrowaveMode.DEFROST, 3:MicrowaveMode.GRILL }

    _M_IoT._t_MicrowaveMode = IcePy.defineEnum('::IoT::MicrowaveMode', MicrowaveMode, (), MicrowaveMode._enumerators)

    _M_IoT.MicrowaveMode = MicrowaveMode
    del MicrowaveMode

if '_t_allowedMicrowaveModes' not in _M_IoT.__dict__:
    _M_IoT._t_allowedMicrowaveModes = IcePy.defineSequence('::IoT::allowedMicrowaveModes', (), _M_IoT._t_MicrowaveMode)

if 'SingleCoffeeOrder' not in _M_IoT.__dict__:
    _M_IoT.SingleCoffeeOrder = Ice.createTempClass()
    class SingleCoffeeOrder(object):
        def __init__(self, coffeeType=_M_IoT.CoffeeType.ESPRESSO, temperature=0, volume=0):
            self.coffeeType = coffeeType
            self.temperature = temperature
            self.volume = volume

        def __hash__(self):
            _h = 0
            _h = 5 * _h + Ice.getHash(self.coffeeType)
            _h = 5 * _h + Ice.getHash(self.temperature)
            _h = 5 * _h + Ice.getHash(self.volume)
            return _h % 0x7fffffff

        def __compare(self, other):
            if other is None:
                return 1
            elif not isinstance(other, _M_IoT.SingleCoffeeOrder):
                return NotImplemented
            else:
                if self.coffeeType is None or other.coffeeType is None:
                    if self.coffeeType != other.coffeeType:
                        return (-1 if self.coffeeType is None else 1)
                else:
                    if self.coffeeType < other.coffeeType:
                        return -1
                    elif self.coffeeType > other.coffeeType:
                        return 1
                if self.temperature is None or other.temperature is None:
                    if self.temperature != other.temperature:
                        return (-1 if self.temperature is None else 1)
                else:
                    if self.temperature < other.temperature:
                        return -1
                    elif self.temperature > other.temperature:
                        return 1
                if self.volume is None or other.volume is None:
                    if self.volume != other.volume:
                        return (-1 if self.volume is None else 1)
                else:
                    if self.volume < other.volume:
                        return -1
                    elif self.volume > other.volume:
                        return 1
                return 0

        def __lt__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r < 0

        def __le__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r <= 0

        def __gt__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r > 0

        def __ge__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r >= 0

        def __eq__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r == 0

        def __ne__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r != 0

        def __str__(self):
            return IcePy.stringify(self, _M_IoT._t_SingleCoffeeOrder)

        __repr__ = __str__

    _M_IoT._t_SingleCoffeeOrder = IcePy.defineStruct('::IoT::SingleCoffeeOrder', SingleCoffeeOrder, (), (
        ('coffeeType', (), _M_IoT._t_CoffeeType),
        ('temperature', (), IcePy._t_short),
        ('volume', (), IcePy._t_short)
    ))

    _M_IoT.SingleCoffeeOrder = SingleCoffeeOrder
    del SingleCoffeeOrder

if '_t_CoffeeOrder' not in _M_IoT.__dict__:
    _M_IoT._t_CoffeeOrder = IcePy.defineSequence('::IoT::CoffeeOrder', (), _M_IoT._t_SingleCoffeeOrder)

if 'Coffee' not in _M_IoT.__dict__:
    _M_IoT.Coffee = Ice.createTempClass()
    class Coffee(object):
        def __init__(self, coffeeType=_M_IoT.CoffeeType.ESPRESSO, temperature=0, volume=0):
            self.coffeeType = coffeeType
            self.temperature = temperature
            self.volume = volume

        def __hash__(self):
            _h = 0
            _h = 5 * _h + Ice.getHash(self.coffeeType)
            _h = 5 * _h + Ice.getHash(self.temperature)
            _h = 5 * _h + Ice.getHash(self.volume)
            return _h % 0x7fffffff

        def __compare(self, other):
            if other is None:
                return 1
            elif not isinstance(other, _M_IoT.Coffee):
                return NotImplemented
            else:
                if self.coffeeType is None or other.coffeeType is None:
                    if self.coffeeType != other.coffeeType:
                        return (-1 if self.coffeeType is None else 1)
                else:
                    if self.coffeeType < other.coffeeType:
                        return -1
                    elif self.coffeeType > other.coffeeType:
                        return 1
                if self.temperature is None or other.temperature is None:
                    if self.temperature != other.temperature:
                        return (-1 if self.temperature is None else 1)
                else:
                    if self.temperature < other.temperature:
                        return -1
                    elif self.temperature > other.temperature:
                        return 1
                if self.volume is None or other.volume is None:
                    if self.volume != other.volume:
                        return (-1 if self.volume is None else 1)
                else:
                    if self.volume < other.volume:
                        return -1
                    elif self.volume > other.volume:
                        return 1
                return 0

        def __lt__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r < 0

        def __le__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r <= 0

        def __gt__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r > 0

        def __ge__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r >= 0

        def __eq__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r == 0

        def __ne__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r != 0

        def __str__(self):
            return IcePy.stringify(self, _M_IoT._t_Coffee)

        __repr__ = __str__

    _M_IoT._t_Coffee = IcePy.defineStruct('::IoT::Coffee', Coffee, (), (
        ('coffeeType', (), _M_IoT._t_CoffeeType),
        ('temperature', (), IcePy._t_short),
        ('volume', (), IcePy._t_short)
    ))

    _M_IoT.Coffee = Coffee
    del Coffee

if '_t_Coffees' not in _M_IoT.__dict__:
    _M_IoT._t_Coffees = IcePy.defineSequence('::IoT::Coffees', (), _M_IoT._t_Coffee)

if 'ValueOutOfRangeException' not in _M_IoT.__dict__:
    _M_IoT.ValueOutOfRangeException = Ice.createTempClass()
    class ValueOutOfRangeException(Ice.UserException):
        def __init__(self, reason=''):
            self.reason = reason

        def __str__(self):
            return IcePy.stringifyException(self)

        __repr__ = __str__

        _ice_id = '::IoT::ValueOutOfRangeException'

    _M_IoT._t_ValueOutOfRangeException = IcePy.defineException('::IoT::ValueOutOfRangeException', ValueOutOfRangeException, (), False, None, (('reason', (), IcePy._t_string, False, 0),))
    ValueOutOfRangeException._ice_type = _M_IoT._t_ValueOutOfRangeException

    _M_IoT.ValueOutOfRangeException = ValueOutOfRangeException
    del ValueOutOfRangeException

if 'UnknownSettingException' not in _M_IoT.__dict__:
    _M_IoT.UnknownSettingException = Ice.createTempClass()
    class UnknownSettingException(Ice.UserException):
        def __init__(self, reason=''):
            self.reason = reason

        def __str__(self):
            return IcePy.stringifyException(self)

        __repr__ = __str__

        _ice_id = '::IoT::UnknownSettingException'

    _M_IoT._t_UnknownSettingException = IcePy.defineException('::IoT::UnknownSettingException', UnknownSettingException, (), False, None, (('reason', (), IcePy._t_string, False, 0),))
    UnknownSettingException._ice_type = _M_IoT._t_UnknownSettingException

    _M_IoT.UnknownSettingException = UnknownSettingException
    del UnknownSettingException

if 'UnsupportedCoffeeTypeException' not in _M_IoT.__dict__:
    _M_IoT.UnsupportedCoffeeTypeException = Ice.createTempClass()
    class UnsupportedCoffeeTypeException(Ice.UserException):
        def __init__(self, reason=''):
            self.reason = reason

        def __str__(self):
            return IcePy.stringifyException(self)

        __repr__ = __str__

        _ice_id = '::IoT::UnsupportedCoffeeTypeException'

    _M_IoT._t_UnsupportedCoffeeTypeException = IcePy.defineException('::IoT::UnsupportedCoffeeTypeException', UnsupportedCoffeeTypeException, (), False, None, (('reason', (), IcePy._t_string, False, 0),))
    UnsupportedCoffeeTypeException._ice_type = _M_IoT._t_UnsupportedCoffeeTypeException

    _M_IoT.UnsupportedCoffeeTypeException = UnsupportedCoffeeTypeException
    del UnsupportedCoffeeTypeException

if 'UnsupportedMicrowaveModeException' not in _M_IoT.__dict__:
    _M_IoT.UnsupportedMicrowaveModeException = Ice.createTempClass()
    class UnsupportedMicrowaveModeException(Ice.UserException):
        def __init__(self, reason=''):
            self.reason = reason

        def __str__(self):
            return IcePy.stringifyException(self)

        __repr__ = __str__

        _ice_id = '::IoT::UnsupportedMicrowaveModeException'

    _M_IoT._t_UnsupportedMicrowaveModeException = IcePy.defineException('::IoT::UnsupportedMicrowaveModeException', UnsupportedMicrowaveModeException, (), False, None, (('reason', (), IcePy._t_string, False, 0),))
    UnsupportedMicrowaveModeException._ice_type = _M_IoT._t_UnsupportedMicrowaveModeException

    _M_IoT.UnsupportedMicrowaveModeException = UnsupportedMicrowaveModeException
    del UnsupportedMicrowaveModeException

if 'IoTDevice' not in _M_IoT.__dict__:
    _M_IoT.IoTDevice = Ice.createTempClass()
    class IoTDevice(Ice.Value):
        def __init__(self, name='', deviceType='', serialNumber=''):
            self.name = name
            self.deviceType = deviceType
            self.serialNumber = serialNumber

        def ice_id(self):
            return '::IoT::IoTDevice'

        @staticmethod
        def ice_staticId():
            return '::IoT::IoTDevice'

        def __str__(self):
            return IcePy.stringify(self, _M_IoT._t_IoTDevice)

        __repr__ = __str__

    _M_IoT._t_IoTDevice = IcePy.defineValue('::IoT::IoTDevice', IoTDevice, -1, (), False, False, None, (
        ('name', (), IcePy._t_string, False, 0),
        ('deviceType', (), IcePy._t_string, False, 0),
        ('serialNumber', (), IcePy._t_string, False, 0)
    ))
    IoTDevice._ice_type = _M_IoT._t_IoTDevice

    _M_IoT.IoTDevice = IoTDevice
    del IoTDevice

_M_IoT._t_IIoTDevice = IcePy.defineValue('::IoT::IIoTDevice', Ice.Value, -1, (), False, True, None, ())

if 'IIoTDevicePrx' not in _M_IoT.__dict__:
    _M_IoT.IIoTDevicePrx = Ice.createTempClass()
    class IIoTDevicePrx(Ice.ObjectPrx):

        def getDeviceData(self, context=None):
            return _M_IoT.IIoTDevice._op_getDeviceData.invoke(self, ((), context))

        def getDeviceDataAsync(self, context=None):
            return _M_IoT.IIoTDevice._op_getDeviceData.invokeAsync(self, ((), context))

        def begin_getDeviceData(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_IoT.IIoTDevice._op_getDeviceData.begin(self, ((), _response, _ex, _sent, context))

        def end_getDeviceData(self, _r):
            return _M_IoT.IIoTDevice._op_getDeviceData.end(self, _r)

        def changeSettings(self, settings, context=None):
            return _M_IoT.IIoTDevice._op_changeSettings.invoke(self, ((settings, ), context))

        def changeSettingsAsync(self, settings, context=None):
            return _M_IoT.IIoTDevice._op_changeSettings.invokeAsync(self, ((settings, ), context))

        def begin_changeSettings(self, settings, _response=None, _ex=None, _sent=None, context=None):
            return _M_IoT.IIoTDevice._op_changeSettings.begin(self, ((settings, ), _response, _ex, _sent, context))

        def end_changeSettings(self, _r):
            return _M_IoT.IIoTDevice._op_changeSettings.end(self, _r)

        @staticmethod
        def checkedCast(proxy, facetOrContext=None, context=None):
            return _M_IoT.IIoTDevicePrx.ice_checkedCast(proxy, '::IoT::IIoTDevice', facetOrContext, context)

        @staticmethod
        def uncheckedCast(proxy, facet=None):
            return _M_IoT.IIoTDevicePrx.ice_uncheckedCast(proxy, facet)

        @staticmethod
        def ice_staticId():
            return '::IoT::IIoTDevice'
    _M_IoT._t_IIoTDevicePrx = IcePy.defineProxy('::IoT::IIoTDevice', IIoTDevicePrx)

    _M_IoT.IIoTDevicePrx = IIoTDevicePrx
    del IIoTDevicePrx

    _M_IoT.IIoTDevice = Ice.createTempClass()
    class IIoTDevice(Ice.Object):

        def ice_ids(self, current=None):
            return ('::Ice::Object', '::IoT::IIoTDevice')

        def ice_id(self, current=None):
            return '::IoT::IIoTDevice'

        @staticmethod
        def ice_staticId():
            return '::IoT::IIoTDevice'

        def getDeviceData(self, current=None):
            raise NotImplementedError("servant method 'getDeviceData' not implemented")

        def changeSettings(self, settings, current=None):
            raise NotImplementedError("servant method 'changeSettings' not implemented")

        def __str__(self):
            return IcePy.stringify(self, _M_IoT._t_IIoTDeviceDisp)

        __repr__ = __str__

    _M_IoT._t_IIoTDeviceDisp = IcePy.defineClass('::IoT::IIoTDevice', IIoTDevice, (), None, ())
    IIoTDevice._ice_type = _M_IoT._t_IIoTDeviceDisp

    IIoTDevice._op_getDeviceData = IcePy.Operation('getDeviceData', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), ((), _M_IoT._t_DeviceData, False, 0), ())
    IIoTDevice._op_changeSettings = IcePy.Operation('changeSettings', Ice.OperationMode.Idempotent, Ice.OperationMode.Idempotent, False, None, (), (((), _M_IoT._t_NewSettings, False, 0),), (), None, (_M_IoT._t_UnknownSettingException, _M_IoT._t_ValueOutOfRangeException))

    _M_IoT.IIoTDevice = IIoTDevice
    del IIoTDevice

if 'CoffeeMaker' not in _M_IoT.__dict__:
    _M_IoT.CoffeeMaker = Ice.createTempClass()
    class CoffeeMaker(_M_IoT.IoTDevice):
        def __init__(self, name='', deviceType='', serialNumber='', temperature=0, minTemperature=0, maxTemperature=0, volume=0, minVolume=0, maxVolume=0, allowedTypes=None):
            _M_IoT.IoTDevice.__init__(self, name, deviceType, serialNumber)
            self.temperature = temperature
            self.minTemperature = minTemperature
            self.maxTemperature = maxTemperature
            self.volume = volume
            self.minVolume = minVolume
            self.maxVolume = maxVolume
            self.allowedTypes = allowedTypes

        def ice_id(self):
            return '::IoT::CoffeeMaker'

        @staticmethod
        def ice_staticId():
            return '::IoT::CoffeeMaker'

        def __str__(self):
            return IcePy.stringify(self, _M_IoT._t_CoffeeMaker)

        __repr__ = __str__

    _M_IoT._t_CoffeeMaker = IcePy.defineValue('::IoT::CoffeeMaker', CoffeeMaker, -1, (), False, False, _M_IoT._t_IoTDevice, (
        ('temperature', (), IcePy._t_short, False, 0),
        ('minTemperature', (), IcePy._t_short, False, 0),
        ('maxTemperature', (), IcePy._t_short, False, 0),
        ('volume', (), IcePy._t_short, False, 0),
        ('minVolume', (), IcePy._t_short, False, 0),
        ('maxVolume', (), IcePy._t_short, False, 0),
        ('allowedTypes', (), _M_IoT._t_allowedCoffeeTypes, False, 0)
    ))
    CoffeeMaker._ice_type = _M_IoT._t_CoffeeMaker

    _M_IoT.CoffeeMaker = CoffeeMaker
    del CoffeeMaker

_M_IoT._t_ICoffeeMaker = IcePy.defineValue('::IoT::ICoffeeMaker', Ice.Value, -1, (), False, True, None, ())

if 'ICoffeeMakerPrx' not in _M_IoT.__dict__:
    _M_IoT.ICoffeeMakerPrx = Ice.createTempClass()
    class ICoffeeMakerPrx(_M_IoT.IIoTDevicePrx):

        def makeCoffee(self, coffeeType, context=None):
            return _M_IoT.ICoffeeMaker._op_makeCoffee.invoke(self, ((coffeeType, ), context))

        def makeCoffeeAsync(self, coffeeType, context=None):
            return _M_IoT.ICoffeeMaker._op_makeCoffee.invokeAsync(self, ((coffeeType, ), context))

        def begin_makeCoffee(self, coffeeType, _response=None, _ex=None, _sent=None, context=None):
            return _M_IoT.ICoffeeMaker._op_makeCoffee.begin(self, ((coffeeType, ), _response, _ex, _sent, context))

        def end_makeCoffee(self, _r):
            return _M_IoT.ICoffeeMaker._op_makeCoffee.end(self, _r)

        @staticmethod
        def checkedCast(proxy, facetOrContext=None, context=None):
            return _M_IoT.ICoffeeMakerPrx.ice_checkedCast(proxy, '::IoT::ICoffeeMaker', facetOrContext, context)

        @staticmethod
        def uncheckedCast(proxy, facet=None):
            return _M_IoT.ICoffeeMakerPrx.ice_uncheckedCast(proxy, facet)

        @staticmethod
        def ice_staticId():
            return '::IoT::ICoffeeMaker'
    _M_IoT._t_ICoffeeMakerPrx = IcePy.defineProxy('::IoT::ICoffeeMaker', ICoffeeMakerPrx)

    _M_IoT.ICoffeeMakerPrx = ICoffeeMakerPrx
    del ICoffeeMakerPrx

    _M_IoT.ICoffeeMaker = Ice.createTempClass()
    class ICoffeeMaker(_M_IoT.IIoTDevice):

        def ice_ids(self, current=None):
            return ('::Ice::Object', '::IoT::ICoffeeMaker', '::IoT::IIoTDevice')

        def ice_id(self, current=None):
            return '::IoT::ICoffeeMaker'

        @staticmethod
        def ice_staticId():
            return '::IoT::ICoffeeMaker'

        def makeCoffee(self, coffeeType, current=None):
            raise NotImplementedError("servant method 'makeCoffee' not implemented")

        def __str__(self):
            return IcePy.stringify(self, _M_IoT._t_ICoffeeMakerDisp)

        __repr__ = __str__

    _M_IoT._t_ICoffeeMakerDisp = IcePy.defineClass('::IoT::ICoffeeMaker', ICoffeeMaker, (), None, (_M_IoT._t_IIoTDeviceDisp,))
    ICoffeeMaker._ice_type = _M_IoT._t_ICoffeeMakerDisp

    ICoffeeMaker._op_makeCoffee = IcePy.Operation('makeCoffee', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), _M_IoT._t_CoffeeType, False, 0),), (), ((), _M_IoT._t_Coffee, False, 0), (_M_IoT._t_UnsupportedCoffeeTypeException,))

    _M_IoT.ICoffeeMaker = ICoffeeMaker
    del ICoffeeMaker

_M_IoT._t_ISuperCoffeeMaker = IcePy.defineValue('::IoT::ISuperCoffeeMaker', Ice.Value, -1, (), False, True, None, ())

if 'ISuperCoffeeMakerPrx' not in _M_IoT.__dict__:
    _M_IoT.ISuperCoffeeMakerPrx = Ice.createTempClass()
    class ISuperCoffeeMakerPrx(_M_IoT.ICoffeeMakerPrx):

        def makeCustomCoffeeOrder(self, order, context=None):
            return _M_IoT.ISuperCoffeeMaker._op_makeCustomCoffeeOrder.invoke(self, ((order, ), context))

        def makeCustomCoffeeOrderAsync(self, order, context=None):
            return _M_IoT.ISuperCoffeeMaker._op_makeCustomCoffeeOrder.invokeAsync(self, ((order, ), context))

        def begin_makeCustomCoffeeOrder(self, order, _response=None, _ex=None, _sent=None, context=None):
            return _M_IoT.ISuperCoffeeMaker._op_makeCustomCoffeeOrder.begin(self, ((order, ), _response, _ex, _sent, context))

        def end_makeCustomCoffeeOrder(self, _r):
            return _M_IoT.ISuperCoffeeMaker._op_makeCustomCoffeeOrder.end(self, _r)

        @staticmethod
        def checkedCast(proxy, facetOrContext=None, context=None):
            return _M_IoT.ISuperCoffeeMakerPrx.ice_checkedCast(proxy, '::IoT::ISuperCoffeeMaker', facetOrContext, context)

        @staticmethod
        def uncheckedCast(proxy, facet=None):
            return _M_IoT.ISuperCoffeeMakerPrx.ice_uncheckedCast(proxy, facet)

        @staticmethod
        def ice_staticId():
            return '::IoT::ISuperCoffeeMaker'
    _M_IoT._t_ISuperCoffeeMakerPrx = IcePy.defineProxy('::IoT::ISuperCoffeeMaker', ISuperCoffeeMakerPrx)

    _M_IoT.ISuperCoffeeMakerPrx = ISuperCoffeeMakerPrx
    del ISuperCoffeeMakerPrx

    _M_IoT.ISuperCoffeeMaker = Ice.createTempClass()
    class ISuperCoffeeMaker(_M_IoT.ICoffeeMaker):

        def ice_ids(self, current=None):
            return ('::Ice::Object', '::IoT::ICoffeeMaker', '::IoT::IIoTDevice', '::IoT::ISuperCoffeeMaker')

        def ice_id(self, current=None):
            return '::IoT::ISuperCoffeeMaker'

        @staticmethod
        def ice_staticId():
            return '::IoT::ISuperCoffeeMaker'

        def makeCustomCoffeeOrder(self, order, current=None):
            raise NotImplementedError("servant method 'makeCustomCoffeeOrder' not implemented")

        def __str__(self):
            return IcePy.stringify(self, _M_IoT._t_ISuperCoffeeMakerDisp)

        __repr__ = __str__

    _M_IoT._t_ISuperCoffeeMakerDisp = IcePy.defineClass('::IoT::ISuperCoffeeMaker', ISuperCoffeeMaker, (), None, (_M_IoT._t_ICoffeeMakerDisp,))
    ISuperCoffeeMaker._ice_type = _M_IoT._t_ISuperCoffeeMakerDisp

    ISuperCoffeeMaker._op_makeCustomCoffeeOrder = IcePy.Operation('makeCustomCoffeeOrder', Ice.OperationMode.Idempotent, Ice.OperationMode.Idempotent, False, None, (), (((), _M_IoT._t_CoffeeOrder, False, 0),), (), ((), _M_IoT._t_Coffees, False, 0), (_M_IoT._t_UnsupportedCoffeeTypeException, _M_IoT._t_ValueOutOfRangeException))

    _M_IoT.ISuperCoffeeMaker = ISuperCoffeeMaker
    del ISuperCoffeeMaker

if 'Microwave' not in _M_IoT.__dict__:
    _M_IoT.Microwave = Ice.createTempClass()
    class Microwave(_M_IoT.IoTDevice):
        def __init__(self, name='', deviceType='', serialNumber='', power=0, minPower=0, maxPower=0, useSound=False, allowedModes=None):
            _M_IoT.IoTDevice.__init__(self, name, deviceType, serialNumber)
            self.power = power
            self.minPower = minPower
            self.maxPower = maxPower
            self.useSound = useSound
            self.allowedModes = allowedModes

        def ice_id(self):
            return '::IoT::Microwave'

        @staticmethod
        def ice_staticId():
            return '::IoT::Microwave'

        def __str__(self):
            return IcePy.stringify(self, _M_IoT._t_Microwave)

        __repr__ = __str__

    _M_IoT._t_Microwave = IcePy.defineValue('::IoT::Microwave', Microwave, -1, (), False, False, _M_IoT._t_IoTDevice, (
        ('power', (), IcePy._t_short, False, 0),
        ('minPower', (), IcePy._t_short, False, 0),
        ('maxPower', (), IcePy._t_short, False, 0),
        ('useSound', (), IcePy._t_bool, False, 0),
        ('allowedModes', (), _M_IoT._t_allowedMicrowaveModes, False, 0)
    ))
    Microwave._ice_type = _M_IoT._t_Microwave

    _M_IoT.Microwave = Microwave
    del Microwave

_M_IoT._t_IMicrowave = IcePy.defineValue('::IoT::IMicrowave', Ice.Value, -1, (), False, True, None, ())

if 'IMicrowavePrx' not in _M_IoT.__dict__:
    _M_IoT.IMicrowavePrx = Ice.createTempClass()
    class IMicrowavePrx(_M_IoT.IIoTDevicePrx):

        def microwave(self, mode, context=None):
            return _M_IoT.IMicrowave._op_microwave.invoke(self, ((mode, ), context))

        def microwaveAsync(self, mode, context=None):
            return _M_IoT.IMicrowave._op_microwave.invokeAsync(self, ((mode, ), context))

        def begin_microwave(self, mode, _response=None, _ex=None, _sent=None, context=None):
            return _M_IoT.IMicrowave._op_microwave.begin(self, ((mode, ), _response, _ex, _sent, context))

        def end_microwave(self, _r):
            return _M_IoT.IMicrowave._op_microwave.end(self, _r)

        def toggleUsingSound(self, context=None):
            return _M_IoT.IMicrowave._op_toggleUsingSound.invoke(self, ((), context))

        def toggleUsingSoundAsync(self, context=None):
            return _M_IoT.IMicrowave._op_toggleUsingSound.invokeAsync(self, ((), context))

        def begin_toggleUsingSound(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_IoT.IMicrowave._op_toggleUsingSound.begin(self, ((), _response, _ex, _sent, context))

        def end_toggleUsingSound(self, _r):
            return _M_IoT.IMicrowave._op_toggleUsingSound.end(self, _r)

        @staticmethod
        def checkedCast(proxy, facetOrContext=None, context=None):
            return _M_IoT.IMicrowavePrx.ice_checkedCast(proxy, '::IoT::IMicrowave', facetOrContext, context)

        @staticmethod
        def uncheckedCast(proxy, facet=None):
            return _M_IoT.IMicrowavePrx.ice_uncheckedCast(proxy, facet)

        @staticmethod
        def ice_staticId():
            return '::IoT::IMicrowave'
    _M_IoT._t_IMicrowavePrx = IcePy.defineProxy('::IoT::IMicrowave', IMicrowavePrx)

    _M_IoT.IMicrowavePrx = IMicrowavePrx
    del IMicrowavePrx

    _M_IoT.IMicrowave = Ice.createTempClass()
    class IMicrowave(_M_IoT.IIoTDevice):

        def ice_ids(self, current=None):
            return ('::Ice::Object', '::IoT::IIoTDevice', '::IoT::IMicrowave')

        def ice_id(self, current=None):
            return '::IoT::IMicrowave'

        @staticmethod
        def ice_staticId():
            return '::IoT::IMicrowave'

        def microwave(self, mode, current=None):
            raise NotImplementedError("servant method 'microwave' not implemented")

        def toggleUsingSound(self, current=None):
            raise NotImplementedError("servant method 'toggleUsingSound' not implemented")

        def __str__(self):
            return IcePy.stringify(self, _M_IoT._t_IMicrowaveDisp)

        __repr__ = __str__

    _M_IoT._t_IMicrowaveDisp = IcePy.defineClass('::IoT::IMicrowave', IMicrowave, (), None, (_M_IoT._t_IIoTDeviceDisp,))
    IMicrowave._ice_type = _M_IoT._t_IMicrowaveDisp

    IMicrowave._op_microwave = IcePy.Operation('microwave', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), _M_IoT._t_MicrowaveMode, False, 0),), (), ((), IcePy._t_string, False, 0), (_M_IoT._t_UnsupportedMicrowaveModeException,))
    IMicrowave._op_toggleUsingSound = IcePy.Operation('toggleUsingSound', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), None, ())

    _M_IoT.IMicrowave = IMicrowave
    del IMicrowave

_M_IoT._t_IBulbulator = IcePy.defineValue('::IoT::IBulbulator', Ice.Value, -1, (), False, True, None, ())

if 'IBulbulatorPrx' not in _M_IoT.__dict__:
    _M_IoT.IBulbulatorPrx = Ice.createTempClass()
    class IBulbulatorPrx(_M_IoT.IIoTDevicePrx):

        def bulbulbul(self, context=None):
            return _M_IoT.IBulbulator._op_bulbulbul.invoke(self, ((), context))

        def bulbulbulAsync(self, context=None):
            return _M_IoT.IBulbulator._op_bulbulbul.invokeAsync(self, ((), context))

        def begin_bulbulbul(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_IoT.IBulbulator._op_bulbulbul.begin(self, ((), _response, _ex, _sent, context))

        def end_bulbulbul(self, _r):
            return _M_IoT.IBulbulator._op_bulbulbul.end(self, _r)

        @staticmethod
        def checkedCast(proxy, facetOrContext=None, context=None):
            return _M_IoT.IBulbulatorPrx.ice_checkedCast(proxy, '::IoT::IBulbulator', facetOrContext, context)

        @staticmethod
        def uncheckedCast(proxy, facet=None):
            return _M_IoT.IBulbulatorPrx.ice_uncheckedCast(proxy, facet)

        @staticmethod
        def ice_staticId():
            return '::IoT::IBulbulator'
    _M_IoT._t_IBulbulatorPrx = IcePy.defineProxy('::IoT::IBulbulator', IBulbulatorPrx)

    _M_IoT.IBulbulatorPrx = IBulbulatorPrx
    del IBulbulatorPrx

    _M_IoT.IBulbulator = Ice.createTempClass()
    class IBulbulator(_M_IoT.IIoTDevice):

        def ice_ids(self, current=None):
            return ('::Ice::Object', '::IoT::IBulbulator', '::IoT::IIoTDevice')

        def ice_id(self, current=None):
            return '::IoT::IBulbulator'

        @staticmethod
        def ice_staticId():
            return '::IoT::IBulbulator'

        def bulbulbul(self, current=None):
            raise NotImplementedError("servant method 'bulbulbul' not implemented")

        def __str__(self):
            return IcePy.stringify(self, _M_IoT._t_IBulbulatorDisp)

        __repr__ = __str__

    _M_IoT._t_IBulbulatorDisp = IcePy.defineClass('::IoT::IBulbulator', IBulbulator, (), None, (_M_IoT._t_IIoTDeviceDisp,))
    IBulbulator._ice_type = _M_IoT._t_IBulbulatorDisp

    IBulbulator._op_bulbulbul = IcePy.Operation('bulbulbul', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), ((), IcePy._t_string, False, 0), ())

    _M_IoT.IBulbulator = IBulbulator
    del IBulbulator

if 'BaseBulbulator' not in _M_IoT.__dict__:
    _M_IoT.BaseBulbulator = Ice.createTempClass()
    class BaseBulbulator(_M_IoT.IoTDevice):
        def __init__(self, name='', deviceType='', serialNumber='', bulRepeatNumber=0):
            _M_IoT.IoTDevice.__init__(self, name, deviceType, serialNumber)
            self.bulRepeatNumber = bulRepeatNumber

        def ice_id(self):
            return '::IoT::BaseBulbulator'

        @staticmethod
        def ice_staticId():
            return '::IoT::BaseBulbulator'

        def __str__(self):
            return IcePy.stringify(self, _M_IoT._t_BaseBulbulator)

        __repr__ = __str__

    _M_IoT._t_BaseBulbulator = IcePy.defineValue('::IoT::BaseBulbulator', BaseBulbulator, -1, (), False, False, _M_IoT._t_IoTDevice, (('bulRepeatNumber', (), IcePy._t_short, False, 0),))
    BaseBulbulator._ice_type = _M_IoT._t_BaseBulbulator

    _M_IoT.BaseBulbulator = BaseBulbulator
    del BaseBulbulator

# End of module IoT
