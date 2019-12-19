package core.genes;

import java.io.*;
import java.util.Calendar;

public class GenomePersister {

	public void perist(Genome pGenomeOrigin, String pPath) throws FileNotFoundException, IOException {
		save(pGenomeOrigin, pPath);
	}

	public Object peristDummy(Serializable pObject) throws IOException, ClassNotFoundException {
		String lFilename = "abc";
		save(pObject, lFilename );
		return load(lFilename);
	}

	public Genome load(String pFileName) throws FileNotFoundException, IOException, ClassNotFoundException {
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;

		Genome lLoadedObject;
		fileInputStream = new FileInputStream(getPathToFilename(pFileName));
		objectInputStream = new ObjectInputStream(fileInputStream);
		lLoadedObject = (Genome) objectInputStream.readObject();
		objectInputStream.close();
		return lLoadedObject;
	}

	private String getPathToFilename(String pFileName) {
		return "persist/"+pFileName;
	}

	public void save(Serializable pObject, String pFileName) throws FileNotFoundException, IOException {
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;

		fileOutputStream = new FileOutputStream(getPathToFilename(pFileName));
		objectOutputStream = new ObjectOutputStream(fileOutputStream);

		objectOutputStream.writeObject(pObject);
		objectOutputStream.close();
	}
}
