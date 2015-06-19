import java.io.*;

public class Utils
{
  public static synchronized String removeTail (File file, int lines)
  {
    String tailString = tail (file, lines);

      truncateFile (file, tailString.length ());

      return tailString;
  }

  public static synchronized void truncateFile (File file, int lengthToRemove)
  {
    java.io.RandomAccessFile fileHandler = null;
    try
    {
      fileHandler = new java.io.RandomAccessFile (file, "rw");
      long fileLength = fileHandler.length () - 1;

      long truncatedLength = fileLength - lengthToRemove;
      if (truncatedLength < 0)
	{
	  fileHandler.setLength (0);
	}
      else
	{
	  fileHandler.setLength (truncatedLength);
	}

      System.out.println("truncateFile() completed!"); 
    }
    catch (java.io.FileNotFoundException e)
    {
      e.printStackTrace ();
    }
    catch (java.io.IOException e)
    {
      e.printStackTrace ();
    }
    finally
    {
      if (fileHandler != null)
	try
	{
	  fileHandler.close ();
	}
      catch (IOException e)
      {
      }
    }
  }

  public static synchronized String tail (File file, int lines)
  {
    java.io.RandomAccessFile fileHandler = null;
    try
    {
      fileHandler = new java.io.RandomAccessFile (file, "r");
      long fileLength = fileHandler.length () - 1;
      StringBuilder sb = new StringBuilder ();
      int line = 0;

      for (long filePointer = fileLength; filePointer != -1; filePointer--)
	{
	  fileHandler.seek (filePointer);
	  int readByte = fileHandler.readByte ();

	  if (readByte == 0xA)
	    {
	      if (filePointer < fileLength)
		{
		  line = line + 1;
		}
	    }
	  else if (readByte == 0xD)
	    {
	      if (filePointer < fileLength - 1)
		{
		  line = line + 1;
		}
	    }
	  if (line >= lines)
	    {
	      break;
	    }
	  sb.append ((char) readByte);
	}

      String str = sb.toString ();

      return str;
    }
    catch (java.io.FileNotFoundException e)
    {
      e.printStackTrace ();
      return null;
    }
    catch (java.io.IOException e)
    {
      e.printStackTrace ();
      return null;
    }
    finally
    {
      if (fileHandler != null)
	try
	{
	  fileHandler.close ();
	}
      catch (IOException e)
      {
      }
    }
  }

}
