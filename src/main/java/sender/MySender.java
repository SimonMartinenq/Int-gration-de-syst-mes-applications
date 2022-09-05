package sender;

import javax.jms.*;
import javax.jms.QueueConnectionFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MySender {

	public static void main(String[] args) {
		int choice = 1; //1 for queue, 2 for topic
		try{
			
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextJMS.xml");
			QueueConnectionFactory factory = (QueueConnectionFactory) applicationContext.getBean("connectionFactory");

			// Create a connection. See https://docs.oracle.com/javaee/7/api/javax/jms/package-summary.html
			Connection connection = factory.createConnection();

			// Open a session without transaction and acknowledge automatic
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Start the connection
			connection.start();

			if (choice == 1)
			{
				Queue queue = (Queue) applicationContext.getBean("queue");

				// Create a sender
				MessageProducer producer1 = session.createProducer(queue);
				MessageProducer producer2 = session.createProducer(queue);

				// Create a message
				TextMessage message1 = session.createTextMessage("Hello World from queue of producer 1!");
				TextMessage message2 = session.createTextMessage("Hello World from queue of producer 2!");

				// Send the message
				producer1.send(message1);
				producer2.send(message2);
			}
			else
			{
				Topic topic = (Topic) applicationContext.getBean("topic");

				// Create a sender
				MessageProducer producer1 = session.createProducer(topic);
				MessageProducer producer2 = session.createProducer(topic);

				// Create a message
				TextMessage message1 = session.createTextMessage("Hello World from topic of producer 1!");
				TextMessage message2 = session.createTextMessage("Hello World from topic of producer 2!");

				// Send the message
				producer1.send(message1);
				producer2.send(message2);
			}

			// Close the session
			session.close();
			// Close the connection
			connection.close();

		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
