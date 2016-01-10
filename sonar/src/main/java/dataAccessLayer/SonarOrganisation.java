package dataAccessLayer;

import java.util.List;

/**
 * Created by Daniel Hofmeister on 04.01.2016.
 */
public class SonarOrganisation implements IOrganisation{

  private List<Opa> allOpas;

  public SonarOrganisation() {}

  @Override
  public List<Opa> getAllOpas() {
    return allOpas;
  }

  public void addOpa(Opa opa) {
    allOpas.add(opa);
  }

  public void deleteOpa(Opa opa) {
    if (allOpas.contains(opa)) {
      allOpas.remove(opa);
    } else {
      throw new IllegalStateException("Opa was not part of this SonarOrganisation");
    }
  }

@Override
public List<IOma> getAllOmas() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void addOpa(IOpa opa) {
	// TODO Auto-generated method stub
	
}

@Override
public void deleteOpa(IOpa opa) {
	// TODO Auto-generated method stub
	
}

@Override
public List<IProtocol> getProtocols() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void addOma(IOma oma) {
	// TODO Auto-generated method stub
	
}

@Override
public void deleteOma(IOma oma) {
	// TODO Auto-generated method stub
	
}
}
