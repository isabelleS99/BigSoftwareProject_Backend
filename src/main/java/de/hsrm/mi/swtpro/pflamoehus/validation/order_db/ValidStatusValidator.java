package de.hsrm.mi.swtpro.pflamoehus.validation.order_db;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import de.hsrm.mi.swtpro.pflamoehus.order.status.Statuscode;



public class ValidStatusValidator implements ConstraintValidator<ValidStatus,String > {

   

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
        for(Statuscode status: Statuscode.values()){
            if(status.toString().equals(value)){
                return true;
            }
        }
        return false;
    }

   
    
}
