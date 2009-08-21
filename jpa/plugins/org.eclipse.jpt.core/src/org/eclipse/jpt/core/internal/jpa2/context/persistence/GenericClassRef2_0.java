package org.eclipse.jpt.core.internal.jpa2.context.persistence;

import org.eclipse.jpt.core.internal.context.persistence.AbstractClassRef;
import org.eclipse.jpt.core.jpa2.context.java.JavaPersistentType2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.ClassRef2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;

public class GenericClassRef2_0
	extends AbstractClassRef
	implements ClassRef2_0
{

	/**
	 * Construct an "specified" class ref; i.e. a class ref with
	 * an explicit entry in the persistence.xml.
	 */
	public GenericClassRef2_0(PersistenceUnit2_0 parent, XmlJavaClassRef classRef) {
		super(parent, classRef);
	}

	/**
	 * Construct an "implied" class ref; i.e. a class ref without
	 * an explicit entry in the persistence.xml.
	 */
	public GenericClassRef2_0(PersistenceUnit2_0 parent, String className) {
		super(parent, className);
	}

	public void synchronizeStaticMetaModel() {
		JavaPersistentType2_0 jpt = (JavaPersistentType2_0) this.getJavaPersistentType();
		if (jpt != null) {
			jpt.synchronizeStaticMetaModel();
		}
	}

}
