#
# Copyright (C) 2022 ArrowOS
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# Inherit from star device
$(call inherit-product, device/xiaomi/star/lahaina.mk)

# Inherit some common ArrowOS stuff.
$(call inherit-product, vendor/arrow/config/common.mk)

# Call the MiuiCamera setup
$(call inherit-product-if-exists, vendor/xiaomi/star-miuicamera/products/miuicamera.mk)

PRODUCT_NAME := arrow_star
PRODUCT_DEVICE := star
PRODUCT_BRAND := Xiaomi
PRODUCT_MODEL := Mi 11 Ultra
PRODUCT_MANUFACTURER := Xiaomi

TARGET_BOOT_ANIMATION_RES := 1440

PRODUCT_GMS_CLIENTID_BASE := android-xiaomi
