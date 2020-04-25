package sr.serialization;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import sr.proto.AddressBookProtos.Person;

public class ProtoSerialization {

	public static void main(String[] args)
	{
		try {
			new ProtoSerialization().testProto();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testProto() throws IOException
	{
		Person john =
				  Person.newBuilder()
				    .setId(123456)
				    .setName("W³odzimierz Wróblewski")
				    .setEmail("wrobel@poczta.com")
				    .addPhones(
						      Person.PhoneNumber.newBuilder()
						        .setNumber("+48-12-555-4321")
						        .setType(Person.PhoneType.HOME))
				    .addPhones(
						      Person.PhoneNumber.newBuilder()
						        .setNumber("+48-699-989-796")
						        .setType(Person.PhoneType.MOBILE))
					.addSomeFloat(0.1f)
					.addSomeFloat(0.2f)
					.addSomeFloat(0.3f)
				    .build();
		
		byte[] johnser = john.toByteArray();

		System.out.println(johnser.length + " " + Arrays.toString(johnser));
		System.out.println(new String(johnser, StandardCharsets.UTF_8));

		long n = 10000000;
        System.out.println("Performing proto serialization " + n + " times...");
        long start = System.currentTimeMillis();
        for(long i = 0; i < n; i++)
		{
			johnser = john.toByteArray();
		}
        System.out.println("... finished.");
        long finish = System.currentTimeMillis();
        long avgTime = (finish - start) / n;
        System.out.println("Average time: " + avgTime);
	}	
}
