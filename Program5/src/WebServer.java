import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

   public void server( int port ) {

      try ( ServerSocket server = new ServerSocket( port ); ) {
         String html = "<HTML><HEAD><TITLE>Generated Page</TITLE></HEAD><BODY><H1>Hello World!</H!></BODY></HTML>";
         boolean running = true;
         while ( running ) {
            try ( Socket client = server.accept( );
                  BufferedReader input = new BufferedReader(
                        new InputStreamReader( client.getInputStream( ) ) );
                  PrintWriter output = new PrintWriter(
                        client.getOutputStream( ) ); ) {
               String inp = "";
               String [ ] request = new String[3];
               while ( ( inp = input.readLine( ) ) != null ) {
                  System.out.println( ">> " + inp );
                  if ( "" .equals( inp ) ) {
                     break;
                  } else if ( inp.contains( "GET" ) ) {
                     request = inp.split( " " );
                  }
               }              
               if ( request[ 1 ].equals( "/" ) ) {
                  request[ 1 ] += "homepage";
               }
               request[ 1 ] = request[ 1 ].substring( 1 ) + ".html";
               File fin = new File( request[ 1 ] );
               if ( !fin.exists( ) ) {
                  output.println("HTTP/1.0 404 Not Found");
                  output.println("Connection: close");
                  output.println("");
                  output.flush();

               } else {
               output.println("HTTP/1.0 200 OK");
               output.println("Content-Type: text/html");
               output.println("Content-Length: " + fin.length( ) );
               output.println("Connection: close");
               output.println("");
               try( FileReader reader = new FileReader( fin) ) {
                  html = "";
                  while( reader.ready( ) ) {
                     html += (char) reader.read( );
                  }
               } catch (FileNotFoundException e) {
                  e.printStackTrace( );
               } catch ( IOException e ) {
                  e.printStackTrace( );
               }
               output.println( html );
               System.out.println( ">" + html );
               output.println("");
               output.flush( );
               }
            } catch ( Exception e ) {
               e.printStackTrace( );
            }
         }
      } catch ( Exception e ) {
         e.printStackTrace( );
      }
   }

   public static void main( String [ ] args ) {
      WebServer self = new WebServer( );
      self.server( Integer.parseInt( args[ 0 ] ) );
   }

}