import java.io.*;

public class Utils
{

        public static synchronized void writeStringToFile (String str, String fileName) {
                PrintWriter pout = null;
                try {
                        pout = new PrintWriter(fileName);
                        pout.println(str);
                } catch(IOException e) {
                        e.printStackTrace();
                } finally {
                        pout.close();
                }
        }

        public static synchronized void appendToFileFromFile (String toFileName, String fromFileName) {
                try {
                        String source = fromFileName;
                        String dest = toFileName;

                        File fin = new File(source);
                        FileInputStream fis = new FileInputStream(fin);
                        BufferedReader in = new BufferedReader(new InputStreamReader(fis));

                        FileWriter fstream = new FileWriter(dest, true);
                        BufferedWriter out = new BufferedWriter(fstream);

                        String aLine = null;
                        while ((aLine = in.readLine()) != null) {
                                //Process each line and add output to Dest.txt file
                                out.write(aLine);
                                out.newLine();
                        }

                        in.close();
                        out.close();

                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        public static synchronized void prependStringToFile (String str, String fileName) {
                String tempFileName = "temp.txt";
                writeStringToFile(str, tempFileName);

                appendToFileFromFile (tempFileName, fileName); // append from b.txt to temp.txt

                try {
                        // delete b.txt
			new File(fileName).delete();

                        // rename temp.txt to b.txt
                        new File(tempFileName).renameTo(new File(fileName));
                } catch(Exception e) {
                        e.printStackTrace();
                }
        }

	public static synchronized String runTailCommand (String fileName)
	{
		String tailStr = null;

		try {
			Process p = Runtime.getRuntime ().exec ("tail " + fileName);
			tailStr = inputStreamToString (p.getInputStream ());
		}
		catch (IOException e)
		{
			e.printStackTrace ();
		}

		return tailStr; 
	}

	public static synchronized String inputStreamToString (InputStream is) throws IOException
	{
		BufferedReader br = new BufferedReader (new InputStreamReader (is));

		StringBuilder sbuilder = new StringBuilder ();
		String aux;

		while ((aux = br.readLine ()) != null)
		{
			sbuilder.append (aux);
			sbuilder.append ("\n");
		}

		return sbuilder.toString ();
	}

	public static synchronized String removeTail (String fileName)
	{
		String tailString = runTailCommand (fileName);

		truncateFile (new File(fileName), tailString.length ());

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

			System.out.println ("truncateFile() completed!");
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
