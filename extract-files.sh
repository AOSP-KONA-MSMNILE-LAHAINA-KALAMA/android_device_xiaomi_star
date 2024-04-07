#!/bin/bash
#
# Copyright (C) 2016 The CyanogenMod Project
# Copyright (C) 2017-2021 The LineageOS Project
#
# SPDX-License-Identifier: Apache-2.0
#

set -e

DEVICE=star
VENDOR=xiaomi

# Load extract_utils and do some sanity checks
MY_DIR="${BASH_SOURCE%/*}"
if [[ ! -d "${MY_DIR}" ]]; then MY_DIR="${PWD}"; fi

ANDROID_ROOT="${MY_DIR}/../../.."

HELPER="${ANDROID_ROOT}/tools/extract-utils/extract_utils.sh"
if [ ! -f "${HELPER}" ]; then
    echo "Unable to find helper script at ${HELPER}"
    exit 1
fi
source "${HELPER}"

# Default to sanitizing the vendor folder before extraction
CLEAN_VENDOR=true

KANG=
SECTION=

while [ "${#}" -gt 0 ]; do
    case "${1}" in
        -n | --no-cleanup )
                CLEAN_VENDOR=false
                ;;
        -k | --kang )
                KANG="--kang"
                ;;
        -s | --section )
                SECTION="${2}"; shift
                CLEAN_VENDOR=false
                ;;
        * )
                SRC="${1}"
                ;;
    esac
    shift
done

if [ -z "${SRC}" ]; then
    SRC="adb"
fi

function blob_fixup() {
    case "${1}" in
        vendor/bin/hw/dolbycodec2)
            sed -i 's/libavservices_minijail_vendor.so/libavservices_minijail.so\x00\x00\x00\x00\x00\x00\x00/g' "${2}"
            ;;
        vendor/bin/hw/vendor.ozoaudio.media.c2@1.0-service)
            sed -i 's/libavservices_minijail_vendor.so/libavservices_minijail.so\x00\x00\x00\x00\x00\x00\x00/g' "${2}"
            "${PATCHELF}" --add-needed "lib-ozoaudio.so" "${2}"
            ;;
        vendor/etc/camera/pureShot_parameter.xml)
            sed -i 's/=\([0-9]\+\)>/="\1">/g' "${2}"
            ;;
        vendor/etc/camera/star_motiontuning.xml|vendor/etc/camera/mars_motiontuning.xml)
            sed -i 's/xml=version/xml\ version/g' "${2}"
	    ;;
        vendor/lib64/vendor.xiaomi.hardware.cameraperf@1.0-impl.so)
            hexdump -ve '1/1 "%.2X"' "${2}" | sed "s/7C000094881640F9/1F2003D5881640F9/g" | xxd -r -p > "${EXTRACT_TMP_DIR}/${1##*/}"
            mv "${EXTRACT_TMP_DIR}/${1##*/}" "${2}"
            ;;
        vendor/lib64/vendor.qti.hardware.camera.postproc@1.0-service-impl.so)
            hexdump -ve '1/1 "%.2X"' "${2}" | sed "s/8D0A0094AE1640F9/1F2003D5AE1640F9/g" | xxd -r -p > "${EXTRACT_TMP_DIR}/${1##*/}"
            mv "${EXTRACT_TMP_DIR}/${1##*/}" "${2}"
            ;;
        vendor/lib64/hw/camera.xiaomi.so)
            hexdump -ve '1/1 "%.2X"' "${2}" | sed "s/5E070094881640F9/1F2003D5881640F9/g; s/AA060094881640F9/1F2003D5881640F9/g" | xxd -r -p > "${EXTRACT_TMP_DIR}/${1##*/}"
            mv "${EXTRACT_TMP_DIR}/${1##*/}" "${2}"
            ;;
        vendor/etc/init/vendor.xiaomi.hardware.citsensorservice@1.1-service.rc)
            sed -i 's/group system input/group system input\n    task_profiles ServiceCapacityLow/' "${2}"
            ;;
        vendor/etc/init/hw/init.batterysecret.rc|vendor/etc/init/hw/init.mi_thermald.rc)
            sed -i 's/on charger/on property:init.svc.vendor.charger=running/' "${2}"
            ;;
        vendor/etc/sensors/hals.conf)
            sed -i '$a sensors.xiaomi.so' "${2}"
            ;;
    esac
}

# Initialize the helper
setup_vendor "${DEVICE}" "${VENDOR}" "${ANDROID_ROOT}" false "${CLEAN_VENDOR}"

extract "${MY_DIR}/proprietary-files.txt" "${SRC}" "${KANG}" --section "${SECTION}"

"${MY_DIR}/setup-makefiles.sh"
