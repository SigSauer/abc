package com.sigsauer.univ.abc.models.exceptions;

public class NaturalClientInvalidTin extends RuntimeException {

    public NaturalClientInvalidTin(String tin, String dob, boolean sex) {
        super("Natural client has invalid data. Tin: "+ tin +", dob: " + dob + " sex: "+sex);
    }
}
