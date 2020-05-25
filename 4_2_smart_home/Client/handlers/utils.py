from IoT import *
from time import sleep


def get_device_data(handler):
    test_connection(handler.proxy)
    data = handler.proxy.getDeviceData()
    print(handler.name + " data:")
    for name, val in data.items():
        print("- ", name, ": ", val, sep="")
    print()


def change_settings(handler):
    print("Enter desired changes in format settingName = newValue or "
          "empty input to finish.")
    print("Available settings: ", handler.settings)
    changes = dict()
    while True:
        line = input("=======> ")
        if not line:
            break

        try:
            name, value = line.split("=", 1)
        except ValueError:
            print("Error: something went wrong with parsing provided value")
            return
        name = name.strip()
        value = value.strip()
        changes[name] = value

    try:
        test_connection(handler.proxy)
        handler.proxy.changeSettings(changes)
    except (UnknownSettingException, ValueOutOfRangeException) as e:
        print("Error:", e.reason)
    print()


def test_connection(handler):
    problem = True
    try:
        # will return None if there is no problem
        problem = handler.proxy.ice_ping()
    except (Ice.ConnectionRefusedException, Ice.InvocationTimeoutException):
        pass  # problem is True by default

    if problem:
        print("There is some problem with connection, please  wait "
              "when we solve it.")
        reconnect(handler)


def _get_sleep_time(failed_requests):
    if failed_requests <= 2:
        return 1
    elif failed_requests <= 6:
        return 0.5
    elif failed_requests <= 13:
        return 0.25
    else:
        return 0.125


def reconnect(handler):
    failed_requests = 1
    while failed_requests < 29:
        print(".", end="")
        problem = True
        try:
            # will return None if there is no problem
            problem = handler.proxy.ice_ping()
        except (Ice.ConnectionRefusedException,
                Ice.InvocationTimeoutException):
            pass  # problem is True by default

        if problem:
            failed_requests += 1
            sleep_time = _get_sleep_time(failed_requests)
            sleep(sleep_time)
        else:
            print()
            print("Problem solved, continuing previous work.")
            print()
            return

    print()
    print("Major problem with network, abandoning work, please try later. "
          "Sorry for the inconvenience.")
    exit(1)
