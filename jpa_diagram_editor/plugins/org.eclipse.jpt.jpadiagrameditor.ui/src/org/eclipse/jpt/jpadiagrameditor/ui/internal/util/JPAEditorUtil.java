/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2011 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.actions.FormatAllAction;
import org.eclipse.jdt.ui.actions.OrganizeImportsAction;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.jpa.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.facade.EclipseFacade;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.propertypage.JPADiagramPropertyPage;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorImageCreator.RelEndDir;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorImageProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants.ShapeType;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;


public class JPAEditorUtil {
	

	private static IPeServiceUtil peUtil = null;
		
	public static String capitalizeFirstLetter(String s) {
		if (s.length() == 0) return s;
		String res = s.substring(0, 1).toUpperCase(Locale.ENGLISH) + s.substring(1);
		return res;
	}
	
	public static String decapitalizeFirstLetter(String s) {
		if (s.length() == 0) return s;
		String res = s.substring(0, 1).toLowerCase(Locale.ENGLISH) + s.substring(1);
		return res;
	}
	
	public static String revertFirstLetterCase(String s) {
		if ((s == null) || (s.length() == 0))
			return s;
		return (Character.isLowerCase(s.charAt(0)) ? 
				s.substring(0, 1).toUpperCase(Locale.ENGLISH) :
				s.substring(0, 1).toLowerCase(Locale.ENGLISH)) +
				s.substring(1);
	}

	synchronized public static IPeServiceUtil getPeUtil() {
		if (peUtil == null)
			peUtil = new PeServiceUtilImpl();
		return peUtil;
	}
	
	public static boolean equalsIgnoreFirstLetterCase(String s1, String s2) {
		if ((s1 == null) && (s2 == null)) 
			return true;
		if ((s1 == null) || (s2 == null))
			return false;
		if (s1.length() != s2.length())
			return false;
		if (s1.length() == 0) 
			return true;
		return s1.substring(0, 1).equalsIgnoreCase(s2.substring(0, 1)) &&
				s1.substring(1).equals(s2.substring(1));
	}
	
    public static Anchor getAnchor(ContainerShape cs) { 
    	Collection<Anchor> anchors  = cs.getAnchors();
	    return anchors.iterator().next();    	
    }
    
    public static Anchor getAnchor(JavaPersistentType jpt, IFeatureProvider fp) {
    	PictogramElement pe = fp.getPictogramElementForBusinessObject(jpt);
    	if ((pe == null) || (!(pe instanceof ContainerShape)))
    		return null;
    	return getAnchor((ContainerShape)pe);
    }
    
    public static String getText(JavaPersistentAttribute at) {
    	return at.getName();
    }
    
    public static String returnSimpleName(String s) {
    	return s.substring(s.lastIndexOf('.') + 1);
    }
    
    public static String stripQuotes(String s) {
    	if ((s.startsWith("\"")) && (s.endsWith("\"")))  //$NON-NLS-1$ //$NON-NLS-2$
    		return s.substring(1, s.length() - 1);
    	return s;
    }
    
	public static String getTooltipText(JavaPersistentType jpt,
			String superPersistentTypeName) {
		String res = jpt.getName();
		if (superPersistentTypeName != null) {
			return MessageFormat.format(JPAEditorMessages.JPAEditorUtil_inheritTypeTooltipText,
					new Object[] { res, superPersistentTypeName });
		}
		return MessageFormat.format(JPAEditorMessages.JPAEditorUtil_fullnameTooltipText,
				new Object[] { res });
	}

    
    public static String getAttributeTypeName(JavaPersistentAttribute at) {
    	return getAttributeTypeName(at.getResourceAttribute());
    }    
    
    public static String getAttributeTypeName(JavaResourceAttribute at) {
    	return at.getTypeName();
    }        
    
    @SuppressWarnings("restriction")
	public static List<String> getAttributeTypeTypeNames(JavaResourceAttribute at) {
    	ListIterator<String> tt = at.getTypeTypeArgumentNames().iterator();
    	if ((tt == null) || !tt.hasNext()) 
    		return null;
    	LinkedList<String> res = new LinkedList<String>();
	    while (tt.hasNext()) 
	    	res.add(tt.next()); 
    	return res;
    }
    
    public static String getAttributeTypeNameWithGenerics(JavaPersistentAttribute at) {
    	return getAttributeTypeNameWithGenerics(at.getResourceAttribute());
    }

    
    @SuppressWarnings("restriction")
	public static String getAttributeTypeNameWithGenerics(JavaResourceAttribute at) {
    	StringBuilder res = new StringBuilder(getAttributeTypeName(at));
    	ListIterator<String> it = at.getTypeTypeArgumentNames().iterator();
    	if ((it != null) && it.hasNext()) {
	    	res.append('<');
	    	res.append(createCommaSeparatedListOfFullTypeNames(it));
	    	res.append('>');
    	}
    	return res.toString();
    }        
    
    
    public static String[] getGenericsElementTypes(String typeName) {
    	if (typeName.indexOf("<") == -1)									//$NON-NLS-1$
    		return null;
    	String types = typeName.substring(typeName.indexOf("<") + 1, typeName.lastIndexOf(">"));	//$NON-NLS-1$	//$NON-NLS-2$
    	String[] typeNames = types.split(",");		//$NON-NLS-1$
    	for (int i = 0; i < typeNames.length; i++) {
    		typeNames[i] = typeNames[i].trim();
    	}
    	return typeNames;
    }          
    
    public static String createCommaSeparatedListOfSimpleTypeNames(String[] strings) {
    	if ((strings == null) || (strings.length == 0))
    		return null;
    	StringBuilder res = new StringBuilder(JPAEditorUtil.returnSimpleName(strings[0]));
    	for (int i = 1; i < strings.length; i++) {
    		res.append(", ");									//$NON-NLS-1$
    		res.append(JPAEditorUtil.returnSimpleName(strings[i]));
    	}
    	return res.toString();
    }     
    
    public static String createCommaSeparatedListOfSimpleTypeNames(ListIterator<String> strIt) {
    	if ((strIt == null) || !strIt.hasNext()) 
    		return null;
    	StringBuilder res = new StringBuilder(JPAEditorUtil.returnSimpleName(strIt.next()));    	
	    while (strIt.hasNext()) {
	    	res.append(", ");									//$NON-NLS-1$
	    	res.append(JPAEditorUtil.returnSimpleName(strIt.next()));
	    }
    	return res.toString();    	
    }     
    
    public static String createCommaSeparatedListOfFullTypeNames(ListIterator<String> strIt) {
    	if ((strIt == null) || !strIt.hasNext()) 
    		return null;
    	StringBuilder res = new StringBuilder(strIt.next());    	
	    while (strIt.hasNext()) {
	    	res.append(", ");									//$NON-NLS-1$
	    	res.append(strIt.next());
	    }
    	return res.toString();    	
    }                    
    
    
    public static String getText(JavaPersistentType jpt) {
    	return JPAEditorUtil.returnSimpleName(JpaArtifactFactory.instance().getEntityName(jpt));
    }
    
    public static String getTooltipText(JavaPersistentType jpt) {
    	String res = jpt.getName();
    	return MessageFormat.format(JPAEditorMessages.JPAEditorUtil_fullnameTooltipText, new Object[] { res });
    }
    
    public static JavaPersistentType getJPType(ICompilationUnit cu) {
    	String name = cu.getElementName();
		if (!name.endsWith(".java"))				//$NON-NLS-1$
			return null;
    	IType tp = cu.findPrimaryType();
    	if (tp == null)
    		return null;;
    	name = tp.getFullyQualifiedName();
		IJavaProject jp = cu.getJavaProject();
    	JpaProject proj = null;
		try {
			proj = JpaArtifactFactory.instance().getJpaProject(jp.getProject());
		} catch (CoreException e) {
			JPADiagramEditorPlugin.logError("Cannot obtain the JPA project.", e); //$NON-NLS-1$	
		}
		if (proj == null)
			return null;
    	PersistenceUnit pu = JpaArtifactFactory.instance().getPersistenceUnit(proj);
    	PersistentType pt = pu.getPersistentType(name);
		if ((pt == null) || (!(pt instanceof JavaPersistentType)))
			return null;
    	return	(JavaPersistentType)pt;
    }
    
    public static String getNameFromShape(Shape sh) {
    	ContainerShape csh = (ContainerShape)sh;
    	List<Shape> shapes = csh.getChildren();
    	Iterator<Shape> it = shapes.iterator();
    	while (it.hasNext()) {
    		Shape shape = it.next();
    		GraphicsAlgorithm ga = shape.getGraphicsAlgorithm();
    		if (ga instanceof Rectangle) {
    			Rectangle rect = (Rectangle)ga;
    			if (rect.getY() == 0) {
    				List<GraphicsAlgorithm> gas = rect.getGraphicsAlgorithmChildren();
    				if (gas.size() > 0) { 
    					GraphicsAlgorithm gra = gas.get(0);
    					if (gra instanceof Text)
    						return ((Text)gra).getValue(); 
    				}
    			}
    		}
    	}
    	return null;
    }
    
    public static void setNameOfShape(Shape sh, String name) {
    	ContainerShape csh = (ContainerShape)sh;
    	List<Shape> shapes = csh.getChildren();
    	Iterator<Shape> it = shapes.iterator();
    	while (it.hasNext()) {
    		Shape shape = it.next();
    		GraphicsAlgorithm ga = shape.getGraphicsAlgorithm();
    		if (ga instanceof Rectangle) {
    			Rectangle rect = (Rectangle)ga;
    			if (rect.getY() == 0) {
    				List<GraphicsAlgorithm> gas = rect.getGraphicsAlgorithmChildren();
    				if (gas.size() > 0) { 
    					GraphicsAlgorithm gra = gas.get(0);
    					if (gra instanceof Text) {
    						((Text)gra).setValue(name);
    						return;
    					}
    				}
    			}
    		}
    	};
    }
    
    public static void setJPTNameInShape(ContainerShape cs, String newName) {
    	setJPTNameInShape(cs, newName, getPeUtil());
    }
    
    public static void setJPTNameInShape(ContainerShape cs, String newName, IPeServiceUtil peUtil) {
    	List<Shape> shapes = cs.getChildren();
    	Iterator<Shape> it = shapes.iterator();
    	while (it.hasNext()) {
    		Shape sh = it.next();
    		String propShapeType = peUtil.getPropertyValue(sh, JPAEditorConstants.PROP_SHAPE_TYPE);
    		if (!ShapeType.HEADER.toString().equals(propShapeType))
    			continue;
    		Text txt = (Text)sh.getGraphicsAlgorithm().getGraphicsAlgorithmChildren().get(0);
    		txt.setValue(newName);
    		return;
    	}
    }
    
    
    public static String produceValidAttributeName(String name) {
    	if ((name == null) || (name.length() == 0)) 
			return "";										//$NON-NLS-1$
    	if (name.length() == 1)
    		return name.toLowerCase(Locale.ENGLISH);
    	String secondSymbol = name.substring(1, 2); 
    	if (secondSymbol.toLowerCase(Locale.ENGLISH).equals(secondSymbol.toUpperCase(Locale.ENGLISH)))
    		return decapitalizeFirstLetter(name);
    	return (isUpperCase(secondSymbol)) ? 
    			capitalizeFirstLetter(name) : 
    				decapitalizeFirstLetter(name);
    }
    
    public static boolean isUpperCase(String s) {
    	if ((s == null) || (s.length() == 0) || (s.length() > 1)) 
    			throw new IllegalArgumentException("The given string has to contain one symbol exactly");	//$NON-NLS-1$
    	return s.toUpperCase(Locale.ENGLISH).equals(s);
    }
    
    
	public static void createRegisterEntityInXMLJob(final JpaProject jpaProject, final String classFQN) {
		final JpaXmlResource resource = jpaProject.getPersistenceXmlResource();
		resource.modify(new Runnable() {
			public void run() {
				XmlPersistence xmlPersistence = (XmlPersistence) resource.getRootObject();
				EList<XmlPersistenceUnit> persistenceUnits = xmlPersistence.getPersistenceUnits();
				XmlPersistenceUnit persistenceUnit = persistenceUnits.get(0); // Multiply persistence unit support
				boolean registered = false;
				Iterator<XmlJavaClassRef> it = persistenceUnit.getClasses().iterator();
				while (it.hasNext()) {
					String className = it.next().getJavaClass();
					if (classFQN.equals(className)) {
						registered = true;
						break;
					}
				}
				if (!registered) {
					XmlJavaClassRef classRef = PersistenceFactory.eINSTANCE.createXmlJavaClassRef();
					classRef.setJavaClass(classFQN);
			     	persistenceUnit.getClasses().add(classRef);					
				}
			}
		});
	}
    
	public static void createUnregisterEntityFromXMLJob(final JpaProject jpaProject, final String classFQN) {
		final JpaXmlResource resource = jpaProject.getPersistenceXmlResource();

		resource.modify(new Runnable() {
			public void run() {
				XmlPersistence xmlPersistence = (XmlPersistence) resource.getRootObject();
				EList<XmlPersistenceUnit> persistenceUnits = xmlPersistence.getPersistenceUnits();
				XmlPersistenceUnit persistenceUnit = persistenceUnits.get(0);// Multiply persistence unit support
				EList<XmlJavaClassRef> cRefs = persistenceUnit.getClasses();
				for (XmlJavaClassRef cRef : cRefs)
					if (cRef.getJavaClass().equals(classFQN)) {
						cRefs.remove(cRef);
						break;
					}
			}
		});
	}
	
	public static void createImports(ICompilationUnit cu, String typeFQN) {
		if (typeFQN == null)
			return;
		typeFQN = typeFQN.trim();
		String[] typeFQNs = getAllTypes(typeFQN);
		createImports(cu, typeFQNs);
	}
		
	public static String[] getAllTypes(String typeFQN){
		typeFQN = typeFQN.replace('[', ',');
		typeFQN = typeFQN.replace(']', ',');
		typeFQN = typeFQN.replace('<', ',');
		typeFQN = typeFQN.replace('>', ',');
		String[] res = typeFQN.trim().split(",");	//$NON-NLS-1$
		for (int i = 0; i < res.length; i++)
			res[i] = res[i].trim();
		return res;
	}
	
	public static void createImports(ICompilationUnit cu, String[] typeFQNs) {
		NullProgressMonitor npm = new NullProgressMonitor();
		for (String typeFQN : typeFQNs) {
			if (typeFQN.startsWith("java.lang.") || !typeFQN.contains("."))		//$NON-NLS-1$	//$NON-NLS-2$
				continue;
			try {
				cu.createImport(typeFQN, null, npm);
			} catch (JavaModelException e) {}
		}
	}
	
	public static String createImport(ICompilationUnit cu, String typeFQN) {
		if (typeFQN.startsWith("java.lang.") || !typeFQN.contains(".")) 		//$NON-NLS-1$	//$NON-NLS-2$
			return returnSimpleName(typeFQN);			
		
		NullProgressMonitor npm = new NullProgressMonitor();
		String simpleName = returnSimpleName(typeFQN);
		IImportDeclaration[] ids = new IImportDeclaration[0];
		try {
			ids = cu.getImports();
		} catch (JavaModelException e) {}		
		for (IImportDeclaration id : ids) {
			String impName = id.getElementName(); 
			if (impName.endsWith("*"))						//$NON-NLS-1$
				continue;
			if (impName.endsWith("." + simpleName))			//$NON-NLS-1$
				return typeFQN;
			if (JPAEditorConstants.WRAPPER_SIMPLE_NAMES.contains(simpleName))
				return typeFQN;
		}
		try {
			cu.createImport(typeFQN, null, npm);
			return returnSimpleName(typeFQN);
		} catch (JavaModelException e) {}
		return typeFQN;
	}

	public static Image createAttributeIcon(Rectangle iconRect, Set<String> annotations) {
		Image icon = null;
		if ((annotations == null) || (annotations.size() == 0) || annotations.contains(JPAEditorConstants.ANNOTATION_BASIC)) {
			icon = Graphiti.getGaService().createImage(iconRect, JPAEditorImageProvider.ICON_BASIC);
		} else if (annotations.contains(JPAEditorConstants.ANNOTATION_ID)) {
			icon = Graphiti.getGaService().createImage(iconRect, JPAEditorImageProvider.PRIMARY_KEY);
		} else if (annotations.contains(JPAEditorConstants.ANNOTATION_ONE_TO_ONE)) {
			icon = Graphiti.getGaService().createImage(iconRect, JPAEditorImageProvider.ICON_ONE_TO_ONE);
		} else if (annotations.contains(JPAEditorConstants.ANNOTATION_ONE_TO_MANY)) {
			icon = Graphiti.getGaService().createImage(iconRect, JPAEditorImageProvider.ICON_ONE_TO_MANY);
		} else if (annotations.contains(JPAEditorConstants.ANNOTATION_MANY_TO_ONE)) {
			icon = Graphiti.getGaService().createImage(iconRect, JPAEditorImageProvider.ICON_MANY_TO_ONE);
		} else if (annotations.contains(JPAEditorConstants.ANNOTATION_MANY_TO_MANY)) {
			icon = Graphiti.getGaService().createImage(iconRect, JPAEditorImageProvider.ICON_MANY_TO_MANY);		
		} else if(annotations.contains(JPAEditorConstants.ANNOTATION_EMBEDDED_ID)){
			icon = Graphiti.getGaService().createImage(iconRect, JPAEditorImageProvider.ICON_EMBEDDED_ID);
		} else if(annotations.contains(JPAEditorConstants.ANNOTATION_VERSION)){
			icon = Graphiti.getGaService().createImage(iconRect, JPAEditorImageProvider.ICON_VERSION);
		} else if(annotations.contains(JPAEditorConstants.ANNOTATION_TRANSIENT)){
			icon = Graphiti.getGaService().createImage(iconRect, JPAEditorImageProvider.ICON_TRANSIENT);
		} else if(annotations.contains(JPAEditorConstants.ANNOTATION_EMBEDDED)){
			icon = Graphiti.getGaService().createImage(iconRect, JPAEditorImageProvider.ICON_EMBEDDED);
		} else {
			icon = Graphiti.getGaService().createImage(iconRect, JPAEditorImageProvider.ICON_BASIC);			
		}	
		return icon;
	}
	
	public static Shape getIconShape(Shape attributeShape) {
		int y = attributeShape.getGraphicsAlgorithm().getY();
		ContainerShape cs = attributeShape.getContainer();
		Iterator<Shape> it = cs.getChildren().iterator();
		while (it.hasNext()) {
			Shape shp = it.next();
			GraphicsAlgorithm ga = shp.getGraphicsAlgorithm(); 
			if ((ga != null) && (ga.getY() == y) && 
				(ShapeType.ICON.toString().
						equals(Graphiti.getPeService().getPropertyValue(shp, 
								JPAEditorConstants.PROP_SHAPE_TYPE)))) 
				return shp;
		}
		return null;
	}
	
	public static Collection<ContainerShape> getRelatedShapes(ContainerShape cs) {
		Collection<ContainerShape> res = new HashSet<ContainerShape>();
		Collection<Connection> cons = new HashSet<Connection>();  
		Iterator<Anchor> ansIt = cs.getAnchors().iterator();
		while (ansIt.hasNext()) {
			Anchor an = ansIt.next();
			cons.addAll(an.getIncomingConnections());
			cons.addAll(an.getOutgoingConnections());
		}
		Iterator<Connection> consIt = cons.iterator();
		while (consIt.hasNext()) {
			Connection con = consIt.next();
			ContainerShape cs1 = (ContainerShape)con.getStart().getParent(); 
			if (cs1 != cs)
				res.add(cs1);
			cs1 = (ContainerShape)con.getEnd().getParent();
			if (cs1 != cs)
				res.add(cs1);
		}
		return res;
	}
		
	public static int calcConnectionLength(FreeFormConnection c) {
		List<org.eclipse.graphiti.mm.algorithms.styles.Point> pts = c.getBendpoints();
		int len = 0;
		for (int i = 0; i < pts.size() - 1; i++) {
			len = len + ((pts.get(i).getX() == pts.get(i + 1).getX()) ?
					Math.abs(pts.get(i).getY() - pts.get(i + 1).getY()) :
					Math.abs(pts.get(i).getX() - pts.get(i + 1).getX()));	
		}
		return len;
	}
		
	public static List<Point> createBendPointList(FreeFormConnection c,  boolean selfRelation) {
		int cnt = getNumberOfConnectionsWithSameEndsAs(c);
		return createBendPointList(c, cnt - 1, cnt, selfRelation);
	}
		
	public static List<Point> createBendPointList(FreeFormConnection c,  int cnt, int connectionsNum,  boolean selfRelation) {
		return selfRelation ? 
				createBendPointListForSelfRelation(c, cnt, connectionsNum) :
				createBendPointListForNonSelfRelation(c, cnt, connectionsNum);
	}
	
	private static List<Point> createBendPointListForSelfRelation(FreeFormConnection c,  int cnt, int connectionsNum) {
		final int ABS_SHIFT = 15; 
		
		int emptiestQuadrant = getEmptiestQuadrant(c);
		boolean evenQuadrant = (emptiestQuadrant == 2) || (emptiestQuadrant == 4);
		
		RoundedRectangle rect = (RoundedRectangle)c.getStart().getParent().getGraphicsAlgorithm();
		
		float halfWidth = rect.getWidth() / 2.0f;
		float halfHeight = rect.getHeight() / 2.0f;
		
		int centerX = Math.round(halfWidth + rect.getX());
		int centerY = Math.round(halfHeight + rect.getY());
		
		boolean startHorizontal = false;
		boolean endHorizontal = !startHorizontal;	

		
		int startDirection = ((emptiestQuadrant == 2) || (emptiestQuadrant == 3)) ? 1 : -1;  // 1 or -1; 1 for RIGHT/DOWN; -1 for LEFT/UP
		int endDirection = ((emptiestQuadrant == 1) || (emptiestQuadrant == 2)) ? 1 : -1;
		
		int x;
		int y; 
		
		
		if (endHorizontal) {
			x = centerX + Math.round(halfWidth * endDirection); 
			y = centerY;
		} else {
			x = centerX; 
			y = centerY + Math.round(halfHeight * endDirection);			
		}
		int SHIFT = evenQuadrant ? -ABS_SHIFT * endDirection : ABS_SHIFT * endDirection;	
		Point endPoint = new Point(x + (endHorizontal ? 0 : (cnt * SHIFT)), y + (endHorizontal ? (cnt * SHIFT) : 0));
		
		
		if (startHorizontal) {
			x = centerX + Math.round(halfWidth * startDirection); 
			y = centerY;
		} else {
			x = centerX; 
			y = centerY + Math.round(halfHeight * startDirection);			
		}
		SHIFT = evenQuadrant ? -ABS_SHIFT * startDirection : ABS_SHIFT * startDirection;		
		Point startPoint = new Point(x + (startHorizontal ? 0 : (cnt * SHIFT)), y + (startHorizontal ? (cnt * SHIFT) : 0));
		
		// second point
		List<Point> res = new LinkedList<Point>();
		res.add(startPoint);
		if (startHorizontal) {
			x = startPoint.x + startDirection * 50 + ((evenQuadrant ? - cnt : cnt) * SHIFT);
			y = startPoint.y;
		} else {
			x = startPoint.x;
			y = startPoint.y + startDirection * 50 + ((evenQuadrant ? - cnt : cnt) * SHIFT);
		}
		res.add(new Point(x, y));
		
		// middle point
		SHIFT = ABS_SHIFT * endDirection;
		if (startHorizontal) {
			y = y + Math.round(halfHeight + 50) * endDirection + (2 *cnt * SHIFT);
		} else {
			x = x + Math.round(halfWidth + 50) * endDirection + (2 * cnt * SHIFT);
		}
		res.add(new Point(x, y));
		
		// fourth point
		if (startHorizontal) {
			x = endPoint.x;
		} else {
			y = endPoint.y;
		}
		res.add(new Point(x, y));
		
		
		res.add(endPoint);
		
		//------------------		
		return res;
	}
	
	private static int getEmptiestQuadrant(FreeFormConnection selfRel) {
		Anchor anchor = selfRel.getStart();
		RoundedRectangle primRect = (RoundedRectangle)anchor.getParent().getGraphicsAlgorithm();
		Collection<Connection> cons = new HashSet<Connection>();
		cons.addAll(anchor.getIncomingConnections());
		cons.addAll(anchor.getOutgoingConnections());
		Iterator<Connection> it = cons.iterator();
		Collection<Anchor> anchors = new HashSet<Anchor>();
		while (it.hasNext()) {
			Connection c = it.next();
			Anchor a = c.getStart();
			if (a != anchor)
				anchors.add(a);
			a = c.getEnd();
			if (a != anchor)
				anchors.add(a);			
		}
		Iterator<Anchor> itAnchors = anchors.iterator();
		float[] quadrantHabitat = new float[5];
		while (itAnchors.hasNext()) {
			Anchor a = itAnchors.next();
			RoundedRectangle rect = (RoundedRectangle)a.getParent().getGraphicsAlgorithm();
			int q = getQuadrant(primRect, rect);
			quadrantHabitat[q] += 1.5f;
			if (q == 1) {
				quadrantHabitat[4] = quadrantHabitat[4] + 0.5f;
			} else if (q == 4) {
				quadrantHabitat[1] +=  0.5f;
			}
			if (q < 4) {
				quadrantHabitat[q + 1] += 0.5f;
			}
			if (q > 1) {
				quadrantHabitat[q - 1] += 0.5f;
			}			
		}
		float minHabitat = Float.MAX_VALUE;
		int emptiestQuadrant = 0;
		for (int i = 1; i < 5; i++) {
			if (quadrantHabitat[i] < minHabitat) {
				emptiestQuadrant = i;
				minHabitat = quadrantHabitat[i]; 
			}
		}
		return emptiestQuadrant;
	}
	
	private static int getQuadrant(RoundedRectangle primRect, RoundedRectangle rect) {
		int primCenterX = primRect.getX() + Math.round(primRect.getWidth() / 2.0f);
		int primCenterY = primRect.getY() + Math.round(primRect.getHeight() / 2.0f);
		int centerX = rect.getX() + Math.round(rect.getWidth() / 2.0f);
		int centerY = rect.getY() + Math.round(rect.getHeight() / 2.0f);
		if (primCenterX < centerX) 
			if (primCenterY > centerY)
				return 1;
			else 
				return 2;
		else 
			if (primCenterY > centerY)
				return 4;
			else 
				return 3;
	}
		
	private static List<Point> createBendPointListForNonSelfRelation(FreeFormConnection c,  int cnt, int connectionsNum) {
		final int ABS_SHIFT = 15; 
		RoundedRectangle rectStart = (RoundedRectangle)c.getStart().getParent().getGraphicsAlgorithm();
		RoundedRectangle rectEnd = (RoundedRectangle)c.getEnd().getParent().getGraphicsAlgorithm();
		
		float startHalfWidth = rectStart.getWidth() / 2.0f;
		float startHalfHeight = rectStart.getHeight() / 2.0f;
		float endHalfWidth = rectEnd.getWidth() / 2.0f;
		float endHalfHeight = rectEnd.getHeight() / 2.0f;

		
		int startCenterX = Math.round(startHalfWidth + rectStart.getX());
		int startCenterY = Math.round(startHalfHeight + rectStart.getY());
		int endCenterX = Math.round(endHalfWidth + rectEnd.getX());
		int endCenterY = Math.round(endHalfHeight + rectEnd.getY());
		int xDelta = endCenterX - startCenterX;
		int yDelta = endCenterY - startCenterY;
		
		boolean startHorizontal = true;
		int x;
		int y; 
		
		if (xDelta != 0) {
			x = startCenterX + Math.round(startHalfWidth * Math.signum(xDelta)); 
			y = startCenterY + Math.round(Math.abs(startHalfWidth / xDelta) * yDelta);
		} else {
			x = startCenterX + Math.round(Math.abs(startHalfHeight / yDelta) * xDelta); 
			y = startCenterY + Math.round(startHalfHeight * Math.signum(yDelta));			
		}
		
		if (Math.abs(y - startCenterY) + 0.1 > startHalfHeight) {
			startHorizontal = false;;
			x = startCenterX + Math.round(Math.abs(startHalfHeight / yDelta) * xDelta);			
			y = startCenterY + Math.round(startHalfHeight * Math.signum(yDelta));
		}
		int SHIFT;
		 if (startHorizontal) {
			 SHIFT = (y < startCenterY) ? ABS_SHIFT : -ABS_SHIFT;
		 } else {
			 SHIFT = (x < startCenterX) ? ABS_SHIFT : -ABS_SHIFT;
		 }

		
		Point startPoint = new Point(x + (startHorizontal ? 0 : (cnt * SHIFT)), y + (startHorizontal ? (cnt * SHIFT) : 0));
		List<Point> res = new LinkedList<Point>();
		
		xDelta = startCenterX - endCenterX;
		yDelta = startCenterY - endCenterY;
		
		boolean straight = (xDelta == 0) || (yDelta == 0); 
		
		res.add(startPoint);
		
		boolean endHorizontal = true;	
		
		if (xDelta != 0) {
			x = endCenterX + Math.round(endHalfWidth * Math.signum(xDelta)); 
			y = endCenterY + Math.round(Math.abs(endHalfWidth / xDelta) * yDelta);
		} else {
			x = endCenterX + Math.round(Math.abs(endHalfHeight / yDelta) * xDelta);
			y = endCenterY + Math.round(endHalfHeight * Math.signum(yDelta)); 
		}
		if (Math.abs(y - endCenterY) + 0.1 > endHalfHeight) {
			endHorizontal = false;
			x = endCenterX + Math.round(Math.abs(endHalfHeight / yDelta) * xDelta);			
			y = endCenterY + Math.round(endHalfHeight * Math.signum(yDelta));
		}
		
		 if (endHorizontal) {
			 SHIFT = (y < endCenterY) ? ABS_SHIFT : -ABS_SHIFT;
		 } else {
			 SHIFT = (x < endCenterX) ? ABS_SHIFT : -ABS_SHIFT;
		 }
		Point endPoint = new Point(x + (endHorizontal ? 0 : (cnt * SHIFT)), y + (endHorizontal ? (cnt * SHIFT) : 0));

		if (!straight) {
			if (startHorizontal && endHorizontal) {
				if (startPoint.y != endPoint.y) {
					x = startPoint.x + Math.round((endPoint.x - startPoint.x) / 2.0f) + cnt * ABS_SHIFT; 
					Point p = new Point(x, startPoint.y);
					res.add(p);
					p = new Point(x, endPoint.y);
					res.add(p);
				}
			} else if (!startHorizontal && !endHorizontal) {
				if (startPoint.x != endPoint.x) {
					y = startPoint.y + Math.round((endPoint.y - startPoint.y) / 2.0f) + cnt * ABS_SHIFT /** (int)Math.signum(yDelta)*/; 
					Point p = new Point(startPoint.x, y);
					res.add(p);
					p = new Point(endPoint.x, y);
					res.add(p);
				}
			} else if (startHorizontal) {
				Point p = new Point(endPoint.x, startPoint.y);
				res.add(p);
			} else if (endHorizontal) {
				Point p = new Point(startPoint.x, endPoint.y);
				res.add(p);			
			}
		}

		res.add(endPoint);				
		return res;
	}
		
	static public void rearrangeAllConnections(final ContainerShape cs, 
											   final IJPAEditorFeatureProvider fp, 
											   final boolean selfOnly) {
		TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(cs);
		ted.getCommandStack().execute(new RecordingCommand(ted) {
			@Override
			protected void doExecute() {
				List<Anchor> anchorsFrom = getAnchors(cs);
				for (Anchor anchorFrom : anchorsFrom) {
					Collection<Connection> outgoingConnections = anchorFrom.getOutgoingConnections();
					Collection<Connection> incomingConnections = anchorFrom.getIncomingConnections();
					
					Set<Set<Connection>> sets = new HashSet<Set<Connection>>();
					Iterator<Connection> it = outgoingConnections.iterator();
					while (it.hasNext()) 
						addConnection(sets, it.next(), selfOnly);
					it = incomingConnections.iterator();
					while (it.hasNext()) 
						addConnection(sets, it.next(), selfOnly);
					
					Iterator<Set<Connection>> setsIter = sets.iterator();
					while (setsIter.hasNext()) {
						Set<Connection> set = setsIter.next();
						int cnt = 0;
						int setSize = set.size();
						Iterator<Connection> cIter = set.iterator();
						while (cIter.hasNext()) {
							Connection c = cIter.next();
							IRelation rel = (IRelation)fp.getBusinessObjectForPictogramElement(c);
							rearrangeConnection(c, cnt, setSize,  rel.getOwner() == rel.getInverse());
							cnt++;
						}
					}
				}
			}			
		});  
	}
	
	static public boolean areConnectionsWithIdenticalEnds(Connection c1, Connection c2) {
		return ((c1.getStart() == c2.getStart()) && (c1.getEnd() == c2.getEnd())) ||
			   ((c1.getStart() == c2.getEnd()) && (c1.getEnd() == c2.getStart()));
	}
	
	static public boolean connectionBelongsToSet(Connection c, Set<Connection> s) {
		if (s.isEmpty())
			return false;
		Connection example = s.iterator().next();
		return areConnectionsWithIdenticalEnds(c, example);
	}
	
	static public int getNumberOfConnectionsWithSameEndsAs(Connection c) {
		Anchor from = c.getStart();
		Anchor to = c.getEnd();
		Collection<Connection> cs1 = from.getOutgoingConnections();
		Collection<Connection> cs2 = to.getOutgoingConnections();
		int cnt = 0;
		Iterator<Connection> it = cs1.iterator();
		while (it.hasNext()) {
			Connection con = it.next();
			if (con.getEnd() == to)
				cnt++;
		}
		if (from == to)
			return cnt;
		it = cs2.iterator();
		while (it.hasNext()) {
			Connection con = it.next();
			if (con.getEnd() == from)
				cnt++;
			}
		return cnt;
	}
	
	static void addConnection(Set<Set<Connection>> sets, Connection c, boolean selfOnly) {
		if (sets == null)
			throw new NullPointerException("'sets' parameter should not be null");	//$NON-NLS-1$
		if (selfOnly && (c.getStart() != c.getEnd()))
			return;
		Iterator<Set<Connection>> setsIter = sets.iterator();
		while (setsIter.hasNext()) {
			Set<Connection> set = setsIter.next();
			if (connectionBelongsToSet(c, set)) {
				sets.remove(set);
				set.add(c);
				sets.add(set);
				return;
		}
	}
		Set<Connection> newSet = new HashSet<Connection>();
		newSet.add(c);
		sets.add(newSet);
	}
	
	static public void rearrangeConnection(Connection connection, int cnt, int connectionsNum, boolean selfRelation) {
		FreeFormConnection c = (FreeFormConnection)connection;
		rearrangeConnectionBendPoints(c, cnt, connectionsNum, selfRelation);
		rearrangeConnectionDecoratorsLocation(c,  cnt, connectionsNum);										
	}
	
	static public void rearrangeConnectionBendPoints(FreeFormConnection c, int cnt, int connectionsNum, boolean selfRelation) {
		List<org.eclipse.graphiti.mm.algorithms.styles.Point> oldPts = c.getBendpoints();
		List<org.eclipse.swt.graphics.Point> newPts = JPAEditorUtil.createBendPointList(c, cnt, connectionsNum,  selfRelation);
		if (newPts == null)
			return;
			
		for (int i = 0; i < Math.min(oldPts.size(), newPts.size()); i++) {
			oldPts.set(i, Graphiti.getGaService().createPoint(/*c, */newPts.get(i).x, newPts.get(i).y));
		}
		int diff = oldPts.size() - newPts.size();
		if (diff > 0) {
			for (int i = oldPts.size() - 1; i >= newPts.size(); i--) 
				oldPts.remove(i);
		} else if (diff < 0) {
			for (int i = oldPts.size(); i < newPts.size(); i++) 
				oldPts.add(Graphiti.getGaService().createPoint(/*c, */newPts.get(i).x, newPts.get(i).y));
		}
	}
	
	static public void rearrangeConnectionDecoratorsLocation(FreeFormConnection c,  int cnt, int connectionsNum) {
		Collection<ConnectionDecorator> dcrs = c.getConnectionDecorators();
				int len = JPAEditorUtil.calcConnectionLength(c);
				for (ConnectionDecorator dcr : dcrs) {
					GraphicsAlgorithm ga = dcr.getGraphicsAlgorithm();
					if (ga instanceof Text) {
						if (Math.abs(dcr.getLocation() - 0.5) < 0.0001) 
							continue;
						Point pt = recalcTextDecoratorPosition(c, dcr);
						Graphiti.getGaService().setLocation(ga, pt.x, pt.y, false);
						continue;
					}
					if (dcr.getLocation() < 0.5) {
						Graphiti.getGaService().setLocation(dcr.getGraphicsAlgorithm(), 
								Math.round(len /10) , 0);
					}
					else {
						Graphiti.getGaService().setLocation(dcr.getGraphicsAlgorithm(), 
								Math.round(-len /10) , 0);
					}
				}
	}
	
	
	public static Point recalcTextDecoratorPosition(FreeFormConnection c, 
											 ConnectionDecorator dcr) {
		int CORR = 5;
		int TXT_HEIGHT = 8;
		double location = dcr.getLocation();
		RelEndDir relEndDir = (location < 0.5) ? 
				JPAEditorUtil.getConnectionStartDir(c) :
					JPAEditorUtil.getConnectionEndDir(c);
		Text txt = (Text)dcr.getGraphicsAlgorithm();
		int TXT_WIDTH = txt.getValue().length() * 6;
		boolean isCardinality = JPAEditorConstants.CARDINALITY_LABELS.contains(txt.getValue()); 
		int x = 0;
		int y = 0;
		if (relEndDir.equals(RelEndDir.LEFT)) {
			x = CORR + (isCardinality ? 2 : 12);
			y = isCardinality ? (-CORR - 3 - TXT_HEIGHT) : CORR - 3;
		} else if (relEndDir.equals(RelEndDir.RIGHT)) {
			x = - TXT_WIDTH;
			y = isCardinality ? (-CORR - 3 - TXT_HEIGHT) : CORR - 3;
		} else if (relEndDir.equals(RelEndDir.UP)) {
			x = isCardinality ? (-CORR - TXT_WIDTH) : CORR;
			y = isCardinality ? CORR : CORR + TXT_HEIGHT + 2;
		} else if (relEndDir.equals(RelEndDir.DOWN)) {
			x = isCardinality ? (-CORR - TXT_WIDTH) : CORR;
			y = -CORR - (isCardinality ? TXT_HEIGHT : (TXT_HEIGHT + 2) * 2);
		}
		return new Point(x, y);
	}
	
	public static String formTableName(JavaPersistentType jpt) { 
		IProject project = jpt.getJpaProject().getProject();
		Properties props = JPADiagramPropertyPage.loadProperties(project);
	    String tableNamePrefix = JPADiagramPropertyPage.getDefaultTablePrefixName(project, props);
	    String shortEntityName = JPAEditorUtil.returnSimpleName(JpaArtifactFactory.instance().getEntityName(jpt)); 
	    if (tableNamePrefix.length() == 0)
	    	return shortEntityName;
		return tableNamePrefix + shortEntityName.toUpperCase(Locale.ENGLISH);
	}
	
	static private List<Anchor> getAnchors(ContainerShape containerShape) {
		List<Anchor> ret = new ArrayList<Anchor>();
		ret.addAll(containerShape.getAnchors());

		List<Shape> children = containerShape.getChildren();
		for (Shape shape : children) {
			if (shape instanceof ContainerShape) {
				ret.addAll(getAnchors((ContainerShape) shape));
			} else {
				ret.addAll(shape.getAnchors());
			}
		}
		return ret;
	}	
	
	static public RelEndDir getConnectionStartDir(FreeFormConnection c) {
		EList<org.eclipse.graphiti.mm.algorithms.styles.Point> pts = c.getBendpoints();
		return getConnectionEndDir(pts.get(0), pts.get(1));
	}
	
	static public RelEndDir getConnectionEndDir(FreeFormConnection c) {
		List<org.eclipse.graphiti.mm.algorithms.styles.Point> pts = c.getBendpoints();
		return getConnectionEndDir(pts.get(pts.size() - 1), pts.get(pts.size() - 2));
	}	
	
	static private RelEndDir getConnectionEndDir(org.eclipse.graphiti.mm.algorithms.styles.Point ptEnd,
			org.eclipse.graphiti.mm.algorithms.styles.Point ptNextToEnd) {
		if (ptEnd.getX() == ptNextToEnd.getX()) {
			return (ptNextToEnd.getY() > ptEnd.getY()) ? RelEndDir.UP : RelEndDir.DOWN;
		} else {
			return (ptNextToEnd.getX() > ptEnd.getX()) ? RelEndDir.LEFT : RelEndDir.RIGHT;
		}		
	}
	
	static public void organizeImports(ICompilationUnit cu, IWorkbenchSite ws) {
		OrganizeImportsAction action = new OrganizeImportsAction(ws);
		action.run(cu);
	}
	
	static public void formatCode(ICompilationUnit cu, IWorkbenchSite ws) {
		FormatAllAction action = new FormatAllAction(ws);
		action.run(new StructuredSelection(cu));
	}
	
	static public String generateUniqueEntityName(JpaProject jpaProject, 
												  String pack, 
												  IJPAEditorFeatureProvider fp) {
    	String NAME = pack + ".Entity"; //$NON-NLS-1$
    	String name = null;
    	
    	HashSet<String> JPAProjectEntityNames = getEntityNames(jpaProject);
       	HashSet<String> JPAProjectEntitySimpleNames = getEntitySimpleNames(jpaProject);
       	
    	for (int i = 1; i < 100000000; i++) {
    		name = NAME + i;
    		if ((!fp.hasObjectWithName(name)) &&
    				!JPAProjectEntityNames.contains(name.toLowerCase(Locale.ENGLISH)) && 
    				!JPAProjectEntitySimpleNames.contains(JPAEditorUtil.returnSimpleName(name).toLowerCase(Locale.ENGLISH)) &&
    				!isJavaFileInProject(jpaProject.getProject(), name, pack)) 
    			break;
    	}
    	return name;
    }
	
	
	static public String generateUniqueMappedSuperclassName(
			JpaProject jpaProject, String pack, IJPAEditorFeatureProvider fp) {
		String NAME = pack + ".MappedSuperclass"; //$NON-NLS-1$
		String name = null;

		HashSet<String> JPAProjectEntityNames = getEntityNames(jpaProject);
		HashSet<String> JPAProjectEntitySimpleNames = getEntitySimpleNames(jpaProject);

		for (int i = 1; i < 100000000; i++) {
			name = NAME + i;
			if ((!fp.hasObjectWithName(name)) && !JPAProjectEntityNames.contains(name
					.toLowerCase(Locale.ENGLISH)) && !JPAProjectEntitySimpleNames.contains(JPAEditorUtil
					.returnSimpleName(name).toLowerCase(Locale.ENGLISH))
					&& !isJavaFileInProject(jpaProject.getProject(), name, pack))
				break;
		}
		return name;
	}
				

	
	static public IFile createEntityInProject(IProject project,
			String entityName, IPreferenceStore jpaPreferenceStore,
			boolean isMappedSuperclassChild, String mappedSuperclassName,
			String mappedSuperclassPackage, boolean hasPrimaryKey)
			throws Exception {
		IFolder folder = getPackageFolder(project);
		return createEntity(project, folder, entityName,
				isMappedSuperclassChild, mappedSuperclassName,
				mappedSuperclassPackage, hasPrimaryKey);
	}

				
	static public IFile createEntityFromMappedSuperclassInProject(
			IProject project, String mappedSuperclassName,
			IPreferenceStore jpaPreferenceStore) throws Exception {
		IFolder folder = getPackageFolder(project);
		createMappedSuperclass(project, folder, mappedSuperclassName);
		return createMappedSuperclass(project, folder, mappedSuperclassName);
	}

	@SuppressWarnings("deprecation")
		private static IFolder getPackageFolder(IProject project) throws JavaModelException {
	
		IJavaProject javaProject = JavaCore.create(project);		
		IPackageFragmentRoot[] packageFragmentRoots = new IPackageFragmentRoot[0];
		final IClasspathEntry[] classpathEntries =  javaProject.getRawClasspath();		
		for (IClasspathEntry classpathEntry : classpathEntries) {
			if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
				packageFragmentRoots = javaProject.getPackageFragmentRoots(classpathEntry);
				break;
			}  			
		}

		IPackageFragmentRoot packageFragmentRoot = packageFragmentRoots[0];
		Properties props = JPADiagramPropertyPage.loadProperties(project);
		IPackageFragment packageFragment = packageFragmentRoot.getPackageFragment(JPADiagramPropertyPage.getDefaultPackage(project, props));
		if(packageFragment.exists() == false) {
			packageFragment = packageFragmentRoot.createPackageFragment(JPADiagramPropertyPage.getDefaultPackage(project, props), true, new NullProgressMonitor());
		}
		IFolder folder = (IFolder) packageFragment.getCorrespondingResource();
		return folder;

	}
	
	static private IFile createMappedSuperclass(IProject project,
			IFolder folder, String mappedSuperclassName) throws Exception {

		String mappedSuperclassShortName = mappedSuperclassName
				.substring(mappedSuperclassName.lastIndexOf('.') + 1);
		if (!folder.exists()) {
			folder.create(true, true, new NullProgressMonitor());
		}
		IFile file = folder.getFile(mappedSuperclassShortName + ".java"); //$NON-NLS-1$

		if (!file.exists()) {
			String content = "package " + JPADiagramPropertyPage.getDefaultPackage(project) + ";\n\n" //$NON-NLS-1$	//$NON-NLS-2$
					+ "import javax.persistence.*;\n\n" //$NON-NLS-1$
					+ "@MappedSuperclass \n" //$NON-NLS-1$
					+ "public class " + mappedSuperclassShortName + " {\n\n" //$NON-NLS-1$ //$NON-NLS-2$
					+ "}"; //$NON-NLS-1$
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			try {
				stream.write(content.getBytes());
				stream.flush();
				file.create(new ByteArrayInputStream(stream.toByteArray()),
						true, new NullProgressMonitor());
			} finally {
				stream.close();
			}
		}
		return file;
	}


	@SuppressWarnings("deprecation")
	static public boolean isJavaFileInProject(IProject project, 
											  String entityName,
											  String defaultEntityPacakage) {
		IJavaProject javaProject = JavaCore.create(project);		
		IPackageFragmentRoot[] packageFragmentRoots = new IPackageFragmentRoot[0];
		IClasspathEntry[] classpathEntries = null;
		try {
			classpathEntries = javaProject.getRawClasspath();
		} catch (JavaModelException e) {
			JPADiagramEditorPlugin.logError("Cannot obtain the classpath", e); //$NON-NLS-1$	
			return false;
		}		
		for (IClasspathEntry classpathEntry : classpathEntries) {
			if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
				packageFragmentRoots = javaProject.getPackageFragmentRoots(classpathEntry);
				break;
			}  			
		}

		IPackageFragmentRoot packageFragmentRoot = packageFragmentRoots[0];
		IPackageFragment packageFragment = packageFragmentRoot.getPackageFragment(JPADiagramPropertyPage.getDefaultPackage(project));
		if(!packageFragment.exists()) 
			return false;
		IFolder folder = null;
		try {
			folder = (IFolder) packageFragment.getCorrespondingResource();
		} catch (JavaModelException e) {
			JPADiagramEditorPlugin.logError("Cannot obtain the folder", e); //$NON-NLS-1$	
			return false;
		}	
		if (!folder.exists()) 
			return false;
		String entityShortName = entityName.substring(entityName
				.lastIndexOf('.') + 1);
		IFile file = folder.getFile(entityShortName + ".java"); //$NON-NLS-1$
		return file.exists();
	}

	
	static public String produceUniqueAttributeName(JavaPersistentType jpt, 
													String attributeNameCandidate) {
		String name = attributeNameCandidate;
		for (int i = 1; i < 100000000; i++) {
			if (jpt.getAttributeNamed(name) == null)
				return name;
			name = attributeNameCandidate + "_" + i; //$NON-NLS-1$
		}
		return name;
	}
	
	static public String produceUniqueAttributeName(JavaPersistentType jpt, String forbiddenName, String attributeNameCandidate) {
		String name = attributeNameCandidate;
		for (int i = 1; i < 100000000; i++) {
			if ((jpt.getAttributeNamed(name) == null) && !name.equals(forbiddenName))
				return name;
			name = attributeNameCandidate + "_" + i; //$NON-NLS-1$
		}
		return name;
	}
	

	public static boolean isEntityOpenElsewhere(JavaPersistentType jpt, boolean checkDirty) {
		IEditorReference[] edRefs = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();
		for (int i = 0; i < edRefs.length; i++) {
			IEditorPart ed = edRefs[i].getEditor(false);
			if (ed == null)
				continue;
			if (checkDirty && !ed.isDirty())
				continue;
			IEditorInput edInp = ed.getEditorInput();
			if (FileEditorInput.class.isInstance(edInp)) {
				FileEditorInput fedInput = (FileEditorInput)edInp; 
				if (fedInput.getFile().equals(jpt.getResource())) {
					return true;
				}
			}
		}
		return false;		
	}
		
	public static ICompilationUnit getCompilationUnit(JavaPersistentType jpt) {
		return getCompilationUnit((IFile) jpt.getResource());
	}		
	
	public static ICompilationUnit getCompilationUnit(IFile file) {
		return EclipseFacade.INSTANCE.getJavaCore().createCompilationUnitFrom(file);
	}
	
	public static void becomeWorkingCopy(ICompilationUnit cu) {
		try {
			cu.becomeWorkingCopy(new NullProgressMonitor());
		} catch (JavaModelException e) {
			JPADiagramEditorPlugin.logError("Can't discard the working copy", e); //$NON-NLS-1$		
		}
	}	
	
	public static void discardWorkingCopy(ICompilationUnit cu) {
		while (cu.isWorkingCopy())
			try {
				cu.discardWorkingCopy();
			} catch (JavaModelException e) {
				JPADiagramEditorPlugin.logError("Can't discard the working copy", e); //$NON-NLS-1$		
			}
	}
	
	public static void discardWorkingCopyOnce(ICompilationUnit cu) {
		if (cu.isWorkingCopy())
			try {
				cu.discardWorkingCopy();
			} catch (JavaModelException e) {
				JPADiagramEditorPlugin.logError("Can't discard the working copy", e); //$NON-NLS-1$		
			}
	}
		
	static private IFile createEntity(IProject project, IFolder folder, String entityName, boolean isMappedSuperclassChild, 
			String mappedSuperclassName, String mappedSuperclassPackage, boolean hasPrimaryKey) throws Exception {


		
		String entityShortName = entityName.substring(entityName.lastIndexOf('.') + 1);		
		if (!folder.exists()) {
			folder.create(true, true, new NullProgressMonitor());				
		}
		IFile file = folder.getFile(entityShortName + ".java");		 //$NON-NLS-1$
		Properties props = JPADiagramPropertyPage.loadProperties(project);
		String tableNamePrefix = JPADiagramPropertyPage.getDefaultTablePrefixName(project, props);
		String tableName = (tableNamePrefix.length() > 0) ? (tableNamePrefix + entityShortName.toUpperCase(Locale.ENGLISH)) : "";	//$NON-NLS-1$
		boolean fieldBasedAccess = JPADiagramPropertyPage.isAccessFieldBased(project, props);
		
		String classDeclarationStringContent = null;
		if (isMappedSuperclassChild) {
			String mappedSuperclassShortName = mappedSuperclassName.substring(mappedSuperclassName.lastIndexOf('.') + 1);
			classDeclarationStringContent = "public class " + entityShortName + " extends " + mappedSuperclassShortName + " {\n\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		} else {
			classDeclarationStringContent = "public class " + entityShortName + " {\n\n"; //$NON-NLS-1$ //$NON-NLS-2$

		}

		String packageImport = ""; //$NON-NLS-1$
		if (mappedSuperclassPackage != null
				&& !mappedSuperclassPackage.equals("") && !(JPADiagramPropertyPage.getDefaultPackage(project, props).equals(mappedSuperclassPackage))) { //$NON-NLS-1$
			packageImport = "import " + mappedSuperclassPackage + ".*;"; //$NON-NLS-1$	//$NON-NLS-2$
		}

		String primaryKeyDeclaration = ""; //$NON-NLS-1$
		if (!hasPrimaryKey) {
			primaryKeyDeclaration = (fieldBasedAccess ? "  @Id \n" : "") //$NON-NLS-1$ //$NON-NLS-2$
					+ "  private long id;\n\n" //$NON-NLS-1$
					+ (fieldBasedAccess ? "" : "  @Id \n") //$NON-NLS-1$ //$NON-NLS-2$
					+ "  public long getId() {\n" //$NON-NLS-1$
					+ "    return id;\n" //$NON-NLS-1$
					+ "  }\n\n" //$NON-NLS-1$
					+ "  public void setId(long id) {\n" //$NON-NLS-1$
					+ "    this.id = id;\n" //$NON-NLS-1$
					+ "  }\n\n"; //$NON-NLS-1$
		}

		
		if (!file.exists()) {
			  String content = "package " + JPADiagramPropertyPage.getDefaultPackage(project, props) + ";\n\n" //$NON-NLS-1$	//$NON-NLS-2$
					+ "import javax.persistence.*;\n"  //$NON-NLS-1$
					+ packageImport+"\n\n" //$NON-NLS-1$
					+ "@Entity \n" //$NON-NLS-1$
					+ ((tableName.length() > 0) ? ("@Table(name=\"" + tableName + "\")\n") : "")  //$NON-NLS-1$	//$NON-NLS-2$	//$NON-NLS-3$
					+ classDeclarationStringContent
					+ primaryKeyDeclaration
					+"}"; //$NON-NLS-1$
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			try {
				stream.write(content.getBytes());
				stream.flush();
				file.create(new ByteArrayInputStream(stream.toByteArray()), true, new NullProgressMonitor());
			} finally {
				stream.close();
			}	
		}
		return file;
	}		
	
	@SuppressWarnings("restriction")
	static private HashSet<String> getEntityNames(JpaProject jpaProject) {
		HashSet<String> names = new HashSet<String>();
		ListIterator<PersistenceUnit> lit = jpaProject.getRootContextNode().getPersistenceXml().
												getPersistence().getPersistenceUnits().iterator();
		PersistenceUnit pu = lit.next();
		for (ClassRef cf : pu.getClassRefs()) {
			names.add(cf.getClassName());
		}
		return names;
	}
	
	@SuppressWarnings("restriction")
	static private HashSet<String> getEntitySimpleNames(JpaProject jpaProject) {
		HashSet<String> names = new HashSet<String>();
		ListIterator<PersistenceUnit> lit = jpaProject.getRootContextNode().getPersistenceXml().
												getPersistence().getPersistenceUnits().iterator();
		PersistenceUnit pu = lit.next();			
		for (ClassRef cf : pu.getClassRefs()) {
			names.add(JPAEditorUtil.returnSimpleName(cf.getClassName()).toLowerCase(Locale.ENGLISH));
		}
		return names;
	}		
	
	public static boolean isCardinalityDecorator(ConnectionDecorator cd) {
		GraphicsAlgorithm ga = cd.getGraphicsAlgorithm();
		if (!Text.class.isInstance(ga))
			return false;
		Text txt = (Text)ga;
		return JPAEditorConstants.CARDINALITY_LABELS.contains(txt.getValue());
	}
	
	
    public static String getTooltipText(JavaPersistentAttribute at) {
    	String res = getAttributeTypeName(at);
    	return MessageFormat.format(JPAEditorMessages.JPAEditorUtil_typeTooltipText, new Object[] { res }); 
    }
    
	static public boolean checkJPAFacetVersion(JpaProject jpaProject, String version) {
		return checkJPAFacetVersion(jpaProject.getProject(), version);
	}	
	
	
	static public boolean checkJPAFacetVersion(IProject project, String version) {
		IFacetedProject fproj = null;
		try {
			fproj = ProjectFacetsManager.create(project);
		} catch (CoreException e) {
			JPADiagramEditorPlugin.logError("Could not create faceted project from " + project.getName(), e); //$NON-NLS-1$		
		}
		Set<IProjectFacetVersion> projFacets = fproj.getProjectFacets();
		Iterator<IProjectFacetVersion> it = projFacets.iterator();
		while (it.hasNext()) {
			IProjectFacetVersion fv = it.next();
			if (fv.getProjectFacet().getId().equals("jpt.jpa")) {	//$NON-NLS-1$
				return fv.getVersionString().equals(version);	
			}
		}
		return false;
	}
	
	static public String getPrimitiveWrapper(String primitive) {
		return JPAEditorConstants.PRIMITIVE_TO_WRAPPER.get(primitive);
	}
	
}