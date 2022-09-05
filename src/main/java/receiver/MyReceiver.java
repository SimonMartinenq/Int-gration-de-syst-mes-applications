package receiver;

import javax.jms.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyReceiver {

	public static void main(String[] args) {
		int choice = 1;// 1 for queue, 2 for topic
		try{
			
			ApplicationContext applicationContext = null;
			applicationContext = new ClassPathXmlApplicationContext("applicationContextJMS.xml");
			QueueConnectionFactory factory = null;
			factory = (QueueConnectionFactory) applicationContext.getBean("connectionFactory");

			// Create a connection. See https://docs.oracle.com/javaee/7/api/javax/jms/package-summary.html
                          
			Connection connection = factory.createConnection();
			connection.start();

			// Open a session
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                        
			// start the connection
			// Create a receiver
			if (choice == 1)
			{
				Queue queue = (Queue) applicationContext.getBean("queue");

				MessageConsumer consumer1;
				consumer1 = session.createConsumer(queue);

				Message message1;
				message1 = consumer1.receive();
				if(message1 instanceof TextMessage){
					TextMessage textMessage1 = (TextMessage) message1;
					System.out.println("Message received by consumer1: " + textMessage1.getText());
				}
				else {
					System.out.println("Message received by consumer1: " + message1.getClass().getName());
				}

			}
			else
			{
				Topic topic = (Topic) applicationContext.getBean("topic");

				MessageConsumer consumer1 = session.createConsumer(topic);

				// Receive the message
				Message message1 = consumer1.receive();
				if(message1 instanceof TextMessage){
					TextMessage textMessage1 = (TextMessage) message1;
					System.out.println("Message received by consumer1: " + textMessage1.getText());
				}
				else {
					System.out.println("Message received by consumer1: " + message1.getClass().getName());
				}
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
