package services.Response;

import model.Person;

/**
 * This class is used to form a Response object to a PersonsFamily request
 */

public class PersonFamilyResponse extends Response{
    private Person[] dataPerson;

    public Person[] getDataPerson() {
        return dataPerson;
    }

    public void setDataPerson(Person[] dataPerson) {
        this.dataPerson = dataPerson;
    }
}
