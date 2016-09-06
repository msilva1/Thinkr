package com.bytes.thinkr.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ValidationInfo {

    public static final ValidationInfo VALID;
    public static final ValidationInfo INVALID;

    static {
        VALID = new ValidationInfo().add(Type.Account, Common.Valid);
        INVALID = new ValidationInfo().add(Type.Account, Common.Invalid);
    }

    public enum Type {

        // Generic
        Entity,

        // Account Services
        Account, Email, Client, UserId, Password,

        // Assignment Services
        Assignment, AssignmentId, Question, Answer,

        // Session Services
        Session
    }

    private HashMap<Type, String> validations;

    public enum Common implements IValidationEnum {

        NotFound,
        Unspecified,
        InvalidQuery,
        Invalid,
        UnableToMerge,
        Valid
    }

    public enum Account implements IValidationEnum {
        InvalidPassword,
        InvalidIdOrPassword
    }

    public enum Email implements IValidationEnum {
        Invalid,
        Valid
    }

    public enum UserId implements IValidationEnum {
        NotAvailable,
        Inappropriate,
        Existing,
    }

    public enum Password implements IValidationEnum {
        TooShort,
        TooSimple
    }

    /** The overall validity of the assignment */
    public enum Assignment implements IValidationEnum {
        Existing,
        AlreadyAssigned,    // already assigned to a specific user; can be assigned to different users
        Assigned 			// successfully assigned an assignment
    }

    /** The validity of the assignment id */
    public enum AssignmentId implements IValidationEnum {
        Existing
    }

    /** The validity of the question id */
    public enum Question implements IValidationEnum {
        Incomplete
    }

    /** The validity of the answer id */
    public enum Answer implements IValidationEnum {
        Correct,
        Incorrect
    }

    public enum Session implements IValidationEnum {
        TimedOut,
        NeverLoggedOn
    }



    public ValidationInfo () {
        validations = new HashMap<>();
    }

    public ValidationInfo add(Type type, IValidationEnum validation) {
        validations.put(
            type,
            validation.toString());
        return this;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for(Type t : validations.keySet()) {
            sb.append(t.toString() + " " + validations.get(t) + System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * Returns the validation information corresponding to the type
     * @param validationType the validation type
     * @return Return <tt>Common.Unspecified</tt> if no validation exists for the specified type
     */
    public String get(Type validationType) {

        if (validations.containsKey(validationType)) {
            return validations.get(validationType);
        } else {
            return Common.Unspecified.toString();
        }
    }

    /**
     * Indicates if there are errors
     * @return true if there are validation errors
     */
    public boolean hasError() {

        if (validations.size() > 0) {
            // If the validation list doesn't have "valid", then it's an error
            return !validations.values().contains(Common.Valid.toString());
        }

        return true;
    }


}
