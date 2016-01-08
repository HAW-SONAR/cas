package dataAccessLayer;

/**
 * Created by Daniel Hofmeister on 04.01.2016.
 */
public class Opa implements IOpa{

  private Oma oma;

  public Opa() {
    oma = null;
  }

  public Oma getOma() {
    return oma;
  }

  public void setOma(Oma oma) {
    this.oma = oma;
  }

  public void removeOma() {
    oma = null;
  }

@Override
public double getOffer(IProtocol p) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public void setTask(IProtocol p) {
	// TODO Auto-generated method stub
	
}

@Override
public String getName() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void start() {
	// TODO Auto-generated method stub
	
}

@Override
public IPlan computeGlobalPlan() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public int getLevel() {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public IPlan exec(IProtocol p) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean deleg(IProtocol p) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean split(IProtocol p) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean refine(IProtocol p) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean isReady() {
	// TODO Auto-generated method stub
	return false;
}
}
