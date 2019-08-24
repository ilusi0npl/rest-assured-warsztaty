package io.swagger.pet.store.tests.chapter4;

import io.qameta.allure.Description;
import io.swagger.pet.store.tests.chapter4.employee.Employee;
import io.swagger.petstore.model.Category;
import io.swagger.petstore.model.Pet;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static io.swagger.petstore.model.Pet.StatusEnum.AVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;

public class PetWithAllureTest {

    private Employee employee;

    @BeforeMethod
    public void beforeMethod() {
        employee = new Employee();
    }

    @Test
    @Description("The goal of this test is to add pet as employee and check if it exist")
    public void asEmployeeAddPetDatabaseAndCheckIfIsPresentTest() {
        Pet initialPet = getPet();
        employee.addAddPet(initialPet);
        Pet actualPet = employee.getPet(initialPet);
        assertThat(actualPet).isEqualToComparingFieldByField(initialPet);
    }

    private Pet getPet() {
        return new Pet().id(TestUtils.nextId()).name("alex").status(AVAILABLE)
                .category(new Category().id(TestUtils.nextId()).name("dog"))
                .photoUrls(Arrays.asList("http://foo.bar.com/1"));
    }

}