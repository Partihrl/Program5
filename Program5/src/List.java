import java.util.ArrayList;
import java.util.Iterator;

public class List<V extends Comparable<V>> implements Iterable<V>, Comparable<List> {
   private class Node {
      private V value = null;
      public V getValue( ) { return value; }
      public void setValue( V newval ) {
         value = newval;
      }
      
      Node next = null;
      
      Node ( V newval, Node nextnode ) {
         value = newval;
         next = nextnode;
      }
   }
      
   private Node start = null;
   
   public boolean isEmpty( ) {
      return start == null || getSize( ) == 0;
   }
   
   private int size = 0;
   public int getSize( ) { return size; }
   
   public void add( int index,  V value ) throws EmptyListException, IndexOutOfBoundsException {
      if ( index < 0 || index > size  ) {
         throw new IndexOutOfBoundsException( );
      }
      Node current = start;
      Node prev = null;
      for( int i = 0; i <= size; i++ ) {
         if ( i == index ) {
            break;
         }
         if ( i == index -1 ) {
            prev = current;
         }
         current = current.next;
      }
      Node newnode = new Node( value, current );
      if ( prev != null ) {
         prev.next = newnode;
      } else {
         start = newnode;
      }
      size++;
   }

   public void add( V value ) throws EmptyListException, IndexOutOfBoundsException {
      add( size, value );
   }
   
   public V first( ) throws EmptyListException {
      if ( isEmpty( ) ) {
         throw new EmptyListException( ); 
      }
      return start.getValue( );
   }
   
   public V get( int index ) throws EmptyListException, IndexOutOfBoundsException {
      if ( isEmpty( ) ) {
         throw new EmptyListException( ); 
      }
      if ( index < 0 || index > size - 1  ) {
         throw new IndexOutOfBoundsException( );
      }
      Node current = start;
      for( int i = 0; i < size; i++ ) {
         if ( i == index ) {
            break;
         }
         current = current.next;
      }
      return current.getValue( );
   }
   
   public V remove( int index ) throws EmptyListException, IndexOutOfBoundsException {
      if ( isEmpty( ) ) {
         throw new EmptyListException( ); 
      }
      if ( index < 0 || index > size - 1  ) {
         throw new IndexOutOfBoundsException( );
      }
      Node current = start;
      Node prev = null;
      for( int i = 0; i < size; i++ ) {
         if ( i == index ) {
            break;
         }
         if ( i == index -1 ) {
            prev = current;
         }
         current = current.next;
      }
      if ( prev != null ) {
         prev.next = current.next;
      } else {
         start = current.next;
      }
      size--;
      return current.getValue( );
   }

   public V remove( ) throws EmptyListException {
      if ( isEmpty( ) ) {
         throw new EmptyListException( ); 
      }
      V result = start.getValue();
      start = start.next;
      return result;
   }
    

   /**
    * @param args
    * @throws EmptyListException 
    * @throws IndexOutOfBoundsException 
    */
   public static void main(String[] args) throws IndexOutOfBoundsException, EmptyListException {
      List<Integer> l1 = new List<Integer>( );
      l1.add( 1 );
      l1.add( 2 );
      l1.add( 3 );
      List<Integer> l2 = new List<Integer>( );
      l2.add( 1 );
      l2.add( 2 );
      l2.add( 3 );
  //    l2.add( 4 );
      System.out.println( "l1=" + l1 );
      System.out.println( "l2=" + l2 );
      System.out.println( "l1 compareTo l2=" + l1.compareTo( l2 ) );
      System.out.println( "l2 compareTo l1=" + l2.compareTo( l1 ) );
      System.out.println( "1234 compareTo 123=" + "1234".compareTo( "123" ) );
   }
   
   @Override 
   public String toString( ) {
      String result = "[";
      for( V v: this ) {
         result += " " + v;
      }
      return result + " ]";
   }

   @Override
   public Iterator< V > iterator( ) {
      ArrayList<V> a = new ArrayList<V>( );
      Node cur = start;
      while( cur != null ) {
         a.add( cur.getValue( ) );
         cur = cur.next;
      }
      return a.iterator( );
   }

   @Override
   public int compareTo( List o ) {
      int result = 0;
      Iterator<V> i1 = this.iterator( );
      Iterator<V> i2 = o.iterator( );
      while( i1.hasNext( ) && i2.hasNext( ) ) {
         result = i1.next( ).compareTo( i2.next( ) );
         if (result != 0) break;
      }
      if ( result == 0 && i1.hasNext( ) != i2.hasNext( ) ) {
         result = ( getSize( ) < o.getSize( ) ) ? -1 : 1;
      }
      return result;
   }
   
   

}