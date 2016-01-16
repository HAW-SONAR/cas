package dataAccessLayer.protocols;

public class IConflictParameterImpl<T> implements IConflictParameter<T> {

	T parameter;
	
	public IConflictParameterImpl(T val) {
		// TODO Auto-generated constructor stub
		this.parameter = val;
	}

	@Override
	public T getValue() {
		// TODO Auto-generated method stub
		return parameter;
	}

}
