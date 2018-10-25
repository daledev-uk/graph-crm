package com.daledev.graphcrm.api.constants;

/**
 * @author dale.ellis
 * @since 16/10/2018
 */
public enum ResultsSortDirection {
    NO_SORT,
    ASCENDING,
    DESCENDING;

    public static ResultsSortDirection toResultsSortDirection(Object inValue) {
        if (inValue == null) {
            return NO_SORT;
        } else if (isDescendingSyntax(inValue)) {
            return DESCENDING;
        } else {
            return ASCENDING;
        }
    }

    public static boolean isDescendingSyntax(Object inValue) {
        return isDescendingBooleanSyntax(inValue) || isDesecndingNumberSyntax(inValue) || isDescendingStringSyntax(inValue);
    }

    public static boolean isDescendingBooleanSyntax(Object inValue) {
        return inValue instanceof Boolean && !((Boolean) inValue);
    }

    public static boolean isDesecndingNumberSyntax(Object inValue) {
        return inValue instanceof Number && ((Number) inValue).intValue() <= 0;
    }

    public static boolean isDescendingStringSyntax(Object inValue) {
        if (inValue instanceof String) {
            String sortAsText = (String) inValue;
            return sortAsText.equalsIgnoreCase("descending") || sortAsText.equalsIgnoreCase("desc");
        }
        return false;
    }
}
