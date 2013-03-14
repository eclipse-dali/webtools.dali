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
package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.JpaContextModelRoot;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.internal.facet.JpaFacetDataModelProperties;
import org.eclipse.jpt.jpa.core.internal.facet.JpaFacetInstallDataModelProperties;
import org.eclipse.jpt.jpa.core.internal.facet.JpaFacetInstallDataModelProvider;
import org.eclipse.jpt.jpadiagrameditor.swtbot.tests.utils.Utils;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

@SuppressWarnings("restriction")
public class JPACreateFactory {
	
	public static final String JPA_JAR_NAME_SYSTEM_PROPERTY = "org.eclipse.jpt.jpa.jar";
	public static final String ECLIPSELINK_JAR_NAME_SYSTEM_PROPERTY = "org.eclipse.jpt.eclipselink.jar";	
	
	private static JPACreateFactory factory = null;
	private IFacetedProject facetedProject; 
	private IProject project; 
	private IJavaProject javaProject;
	//private IPackageFragmentRoot sourceFolder;
//	JpaProject jpaProject;
	
	public static synchronized JPACreateFactory instance() {
		if (factory == null)
			factory = new JPACreateFactory();
		return factory;
	}
	
	private IProject buildPlatformProject(String projectName) throws CoreException {
		IWorkspaceDescription description = ResourcesPlugin.getWorkspace().getDescription();
		description.setAutoBuilding(true);
		ResourcesPlugin.getWorkspace().setDescription(description);		
		IProject p = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		p.create(null);
		p.open(null);
		return p;
	}
	
	public JpaProject createJPAProject(String projectName) throws CoreException {
		return createJPAProject(projectName, null, "1.0");
	}

	public JpaProject createJPA20Project(String projectName) throws CoreException {
		return createJPAProject(projectName, null, "2.0");
	}
	
	
	public JpaProject createJPAProject(String projectName, IDataModel jpaConfig, String jpaFacetVersion) throws CoreException {
		project = buildPlatformProject(projectName);
		javaProject = createJavaProject(project, true);
		if (jpaConfig != null) {
			jpaFacetVersion = jpaConfig.getStringProperty(IFacetDataModelProperties.FACET_VERSION_STR);
		}
		installFacet(facetedProject, "jst.utility", "1.0");
		installFacet(facetedProject, "jpt.jpa", jpaFacetVersion, jpaConfig);
		addPersistenceJarIntoProject(javaProject);
		int cnt = 0;
		JpaProject jpaProject = null;
		while ((jpaProject == null) && (cnt < 1000)){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			jpaProject = this.getJpaProject(project);
			cnt++;
		}
		jpaProject.setDiscoversAnnotatedClasses(true);
//		jpaProject.setUpdater(new SynchronousJpaProjectUpdater(jpaProject));
		return jpaProject;
	}

	public void addPersistenceJarIntoProject(IJavaProject javaProject) throws JavaModelException,
			CoreException {
		addJar(javaProject, jpaJarName());
		if (eclipseLinkJarName() != null) {
			addJar(javaProject, eclipseLinkJarName());
		}
//		project.refreshLocal(IResource.DEPTH_INFINITE, null);
//		jpaProject = this.getJpaProject(project);
	}
	
	public JpaProject getJpaProject(IProject p) {
		return (JpaProject) p.getAdapter(JpaProject.class);
	}

	public static String eclipseLinkJarName() {
		return getSystemProperty(ECLIPSELINK_JAR_NAME_SYSTEM_PROPERTY);
	}
	
	public void installFacet(IFacetedProject facetedProject,
							 String facetName, 
							 String versionName) throws CoreException {
		installFacet(facetedProject, facetName, versionName, null);
	}

	public void uninstallFacet(IFacetedProject facetedProject,
							   String facetName, 
							   String versionName) throws CoreException {
		uninstallFacet(facetedProject, facetName, versionName, null);
	}

	/**
	 * if 'config' is null (and 'facetName' is "jpt.jpa"), the JPA project will be built with the defaults
	 * defined in JpaFacetInstallDataModelProvider#getDefaultProperty(String)
	 */
	public void installFacet(IFacetedProject facetedProject,
							 String facetName, 
							 String versionName, 
							 IDataModel config) throws CoreException {
		facetedProject.installProjectFacet(this.facetVersion(facetName, versionName), config, null);
	}

	public void uninstallFacet(IFacetedProject facetedProject,
							   String facetName, 
							   String versionName, 
							   IDataModel config) throws CoreException {
		facetedProject.uninstallProjectFacet(this.facetVersion(facetName, versionName), config, null);
	}

	private IProjectFacetVersion facetVersion(String facetName, String versionName) {
		return ProjectFacetsManager.getProjectFacet(facetName).getVersion(versionName);
	}
	
	protected static String getSystemProperty(String propertyName) {
		return System.getProperty(propertyName);
	}

	public void addJar(IJavaProject javaProject, String jarPath) throws JavaModelException {
		this.addClasspathEntry(javaProject, JavaCore.newLibraryEntry(new Path(jarPath), null, null));
	}

	private void addClasspathEntry(IJavaProject javaProject, IClasspathEntry entry) throws JavaModelException {
		javaProject.setRawClasspath(ArrayTools.add(javaProject.getRawClasspath(), entry), null);
	}
	
	private IFacetedProject createFacetedProject(IProject project) throws CoreException {
		return ProjectFacetsManager.create(project, true, null);		// true = "convert if necessary"
	}
	
	public IJavaProject createJavaProject(IProject project,  
										  boolean autoBuild) throws CoreException {
		facetedProject = createFacetedProject(project);
		installFacet(facetedProject, "jst.java", "5.0");
		javaProject = JavaCore.create(project);
		//sourceFolder = javaProject.getPackageFragmentRoot(project.getFolder("src"));
		return javaProject;
	}
	
	public static String jpaJarName() {
		return getSystemProperty(JPA_JAR_NAME_SYSTEM_PROPERTY);
	}
	
	public IFile createEntity(JpaProject jpaProject, String fullyQualifiedName) throws Exception {
		StringTokenizer tokenizer = new StringTokenizer(fullyQualifiedName, ".");
		ArrayList<String> nameElements = new ArrayList<String>();
		while(tokenizer.hasMoreElements()){
			nameElements.add(tokenizer.nextToken());
		}
		ArrayList<String> packageFragments = new ArrayList<String>();
		for(int i=0;i<nameElements.size()-1;i++){
			packageFragments.add(nameElements.get(i));
		}
		String[] packageStrings = new String[packageFragments.size()];
		for(int i=0;i<packageFragments.size();i++){
			packageStrings[i] = packageFragments.get(i);
		}
		String name = nameElements.get(Math.max(0, nameElements.size()-1));
//		SynchronousJpaProjectUpdater updater = new SynchronousJpaProjectUpdater(jpaProject);
//		updater.start();
		JpaContextModelRoot jpaProjectContent = jpaProject.getContextModelRoot();
		PersistenceXml persXML = jpaProjectContent.getPersistenceXml();
		int cnt = 0;
		while ((persXML == null) && (cnt < 100)) {		
			Thread.sleep(250);
			persXML = jpaProjectContent.getPersistenceXml();
			cnt++;
		}
		if (persXML == null)
			throw new NullPointerException("The persistence XML is not created");
		
		IFile entity1 = createEntityInProject(jpaProject.getProject(), packageStrings, name);
		JavaResourceAbstractType jrpt = jpaProject.getJavaResourceType(fullyQualifiedName);
		cnt = 0;
		while((jrpt == null) && (cnt < 100)) {
			try {
				Thread.sleep(250);
			} catch (Exception e) {} 
			jrpt = jpaProject.getJavaResourceType(fullyQualifiedName);
			cnt++;
		}
		if (jrpt == null)
			throw new NullPointerException("The entity '" + fullyQualifiedName + "' could not be created");		
		return entity1;
	}
	
	public IFile createEntityInProject(IProject project, 
									   String[] packageFragments, 
									   String entityName) throws IOException, 
									   							 CoreException, 
									   							 JavaModelException {
		String folderName = getFolderName(project, packageFragments);
		String packageName = packageFragments[0];		
		for (int i = 1; i < packageFragments.length; i++) {
			packageName += "." + packageFragments[i];
		}
		
		IPath path = new Path(folderName);
		IFolder folder = project.getFolder(path);		 
		return createEntity(folder, packageName , entityName);
	}

	@SuppressWarnings("deprecation")
	private String getFolderName(IProject project, String[] packageFragments)
			throws JavaModelException {
		IJavaProject javaProject = JavaCore.create(project);		
		IPackageFragmentRoot[] packageFragmentRoots = new IPackageFragmentRoot[0];
		final IClasspathEntry[] classpathEntries =  javaProject.getRawClasspath();		
		for (IClasspathEntry classpathEntry : classpathEntries) {
			if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
				packageFragmentRoots = javaProject.getPackageFragmentRoots(classpathEntry);
				break;
			}  			
		}
		
		String folderName = packageFragmentRoots[0].getResource().getName();
		for (String fragment : packageFragments) {
			folderName += Path.SEPARATOR + fragment;
		}
		return folderName;
	}
	
	private IFile createEntity(IFolder folder, String packageName, String entityName) throws IOException, CoreException {
		String entityShortName = entityName.substring(entityName.lastIndexOf('.') + 1);
		if (!folder.exists()) {
			createDirectories(folder, true, true, new NullProgressMonitor());
		}
		IFile file = folder.getFile(entityShortName + ".java");		
		if (!file.exists()) {
			String content = "package " + packageName + ";\n\n" 
					+ "import javax.persistence.*;\n\n" 
					+ "@Entity \n"
					+ "public class " + entityShortName + " {\n"
					+ "	private int id;\n"
					+ "	@Id \n"
					+ "	public int getId() {\n" 
					+ "		return id;\n"
					+ "	}\n"
					+ "	public void setId(int id) {\n"
					+ "		this.id = id;\n" 
					+ "	}\n"
					+ "}"; //$NON-NLS-1$
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
	
	public IFile createEntityWithCompositePKInProject(IProject project,
			String[] packageFragments, String entityName) throws IOException,
			CoreException, JavaModelException {
		String folderName = getFolderName(project, packageFragments);

		String packageName = packageFragments[0];
		for (int i = 1; i < packageFragments.length; i++) {
			packageName += "." + packageFragments[i];
		}

		IPath path = new Path(folderName);
		IFolder folder = project.getFolder(path);
		return createEntityWithCompositePK(folder, packageName, entityName);
	}

	public IFile createIdClassInProject(IProject project,
			String[] packageFragments, String entityName) throws IOException,
			CoreException, JavaModelException {
		String folderName = getFolderName(project, packageFragments);

		String packageName = packageFragments[0];
		for (int i = 1; i < packageFragments.length; i++) {
			packageName += "." + packageFragments[i];
		}

		IPath path = new Path(folderName);
		IFolder folder = project.getFolder(path);
		return createIdClass(folder, packageName, entityName);
	}

	public IFile createEmbeddedClassInProject(IProject project,
			String[] packageFragments, String entityName) throws IOException,
			CoreException, JavaModelException {
		String folderName = getFolderName(project, packageFragments);

		String packageName = packageFragments[0];
		for (int i = 1; i < packageFragments.length; i++) {
			packageName += "." + packageFragments[i];
		}

		IPath path = new Path(folderName);
		IFolder folder = project.getFolder(path);
		return createEmbeddedClass(folder, packageName, entityName);
	}

	public IFile createEntityWithEmbeddedPKInProject(IProject project,
			String[] packageFragments, String entityName) throws IOException,
			CoreException, JavaModelException {
		String folderName = getFolderName(project, packageFragments);

		String packageName = packageFragments[0];
		for (int i = 1; i < packageFragments.length; i++) {
			packageName += "." + packageFragments[i];
		}

		IPath path = new Path(folderName);
		IFolder folder = project.getFolder(path);
		return createEntityWithEmbeddedPK(folder, packageName, entityName);
	}
	
		
		
		private IFile createEntityWithCompositePK(IFolder folder, String packageName, String entityName) throws IOException, CoreException{
			String entityShortName = entityName.substring(entityName.lastIndexOf('.') + 1);
			if (!folder.exists()) {
				createDirectories(folder, true, true, new NullProgressMonitor());
			}
			IFile file = folder.getFile(entityShortName + ".java");		
			if (!file.exists()) {
				String content = "package " + packageName + ";\n\n" 
						+ "import javax.persistence.*;\n\n" 
						+ "@Entity \n"
						+ "@IdClass("+entityShortName+"Id.class)"
					+ "public class " + entityShortName + " {\n"
						+ "	@Id \n"
						+ "	private String firstName;\n"
						+ "	@Id \n"
						+ "	private String lastName;\n"
						+ " public "+entityShortName+"Id() {}\n"
	                    + " public "+entityShortName+"Id(String firstName, String lastName)\n{"
	                    + "     this.firstName = firstName;\n"
	                    + "     this.lastName = lastName;\n"
	                    + " }\n"
						+ "}"; //$NON-NLS-1$
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
		
		private IFile createIdClass(IFolder folder, String packageName, String entityName) throws IOException, CoreException{
			String entityShortName = entityName.substring(entityName.lastIndexOf('.') + 1);
			if (!folder.exists()) {
				createDirectories(folder, true, true, new NullProgressMonitor());
			}
			IFile file = folder.getFile(entityShortName + ".java");		
			if (!file.exists()) {
				String content = "package " + packageName + ";\n\n" 
						+ "public class " + entityShortName + " {\n"
						+ "	private long id;\n"
						+ "	public long getId() {\n" 
						+ "		return id;\n"
						+ "	}\n"
						+ "	public void setId(long id) {\n"
						+ "		this.id = id;\n" 
						+ "	}\n"
						+ "}"; //$NON-NLS-1$
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
		
		private IFile createEntityWithEmbeddedPK(IFolder folder, String packageName, String entityName) throws IOException, CoreException{
			String entityShortName = entityName.substring(entityName.lastIndexOf('.') + 1);
			if (!folder.exists()) {
				createDirectories(folder, true, true, new NullProgressMonitor());
			}
			IFile file = folder.getFile(entityShortName + ".java");		
			if (!file.exists()) {
				String content = "package " + packageName + ";\n\n" 
						+ "import javax.persistence.*;\n\n" 					
						+ "@Entity\n"
						+ "public class " + entityShortName + " {\n"
						+ "@EmbeddedId\n"
						+ "	private "+ entityShortName +"Id id;\n"						
						+ "public void setId(" + entityShortName+ "Id param) {\n"
						+ "	this.id = param;\n"
						+ "}\n"
						+ "public "+entityShortName+"Id getId() {\n"
						+	"return id;\n"
						+ "}\n"						
						+ "}"; //$NON-NLS-1$
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
		
		private IFile createEmbeddedClass(IFolder folder, String packageName, String entityName) throws IOException, CoreException{
			String entityShortName = entityName.substring(entityName.lastIndexOf('.') + 1);
			if (!folder.exists()) {
				createDirectories(folder, true, true, new NullProgressMonitor());
			}
			IFile file = folder.getFile(entityShortName + ".java");		
		if (!file.exists()) {
			String content = "package " + packageName + ";\n\n"
			                 + "import javax.persistence.*;\n" 
			                 + "@Embeddable\n"
					         + "public class " + entityShortName + " {\n"
					     	 +"private String firstName;\n"
					     	 +"public String getFirstName() {\n"
					     	 + "	return firstName;\n"
					     	 + "}\n"
					     	 + "public void setFirstName(String firstName) {\n"
					     	 +	"this.firstName = firstName;\n"
					     	 + "}\n"
					         + "}"; //$NON-NLS-1$
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
	
	
	private void createDirectories(IContainer container, boolean force,
			boolean local, IProgressMonitor monitor) throws CoreException {
		if (container != null && container instanceof IFolder) {
			IFolder folder = (IFolder) container;
			if (!folder.exists()) {
				// depth first
				IContainer parent = folder.getParent();
				createDirectories(parent, force, local, null);
				// own second
				folder.create(force, local, monitor);
			}
		}
	}
	
	public static PersistentAttribute getPersistentAttribute(IFile entity, String attributeName){
		Set<PersistentAttribute> result = getEntityFields(entity);
		for(PersistentAttribute attribute : result){
			if(attributeName.equals(attribute.getName())){
				return attribute;
			}
		}
		return null;
	}
	
	public static PersistentType getPersistentType(IFile file){
		JpaFile jpaFile = getJpaFile(file);
		for (JpaStructureNode node : getRootNodes(jpaFile)) {
			PersistentType entity = (PersistentType) node;
			return entity;
		}
		return null;
	}

	private static Iterable<JpaStructureNode> getRootNodes(JpaFile jpaFile) {
		if(jpaFile == null){
			return EmptyIterable.instance();
		}
		return jpaFile.getRootStructureNodes();
	}
	
	public static Set<PersistentAttribute> getEntityFields(IFile file){
		Set<PersistentAttribute> result = new HashSet<PersistentAttribute>();
		JpaFile jpaFile = getJpaFile(file);
		if(jpaFile == null){
			return result;
		}
		for (JpaStructureNode node : getRootNodes(jpaFile)) {
			PersistentType entity = (PersistentType) node;
			Iterator<PersistentAttribute> attributes = entity.getAllAttributes().iterator();
			while (attributes.hasNext()){
				PersistentAttribute attribute = attributes.next();
				result.add(attribute);
			}
		}
		return result;
	}

	private static JpaFile getJpaFile(IFile file) {
		return (JpaFile) file.getAdapter(JpaFile.class);
	}
	
	public void addAttributes(IFile entity, String attName, String attType, String annotation, String attActName, boolean isCollection){
		PersistentType javaPersistentType = getPersistentType(entity);
		int cnt = 0;
		while ((javaPersistentType == null) && (cnt < 100)) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {}
			javaPersistentType = getPersistentType(entity);
			cnt++;
		}
		if (javaPersistentType == null)
			throw new RuntimeException("The entity could not be created");
		JpaArtifactFactory.instance().makeNewAttribute(javaPersistentType, attName, attType, attActName, attType, null, null, isCollection);
	}
	
	private IFile createFieldAnnotatedEntity(IFolder folder, String packageName, String entityName) throws IOException, CoreException {
		String entityShortName = entityName.substring(entityName.lastIndexOf('.') + 1);
		if (!folder.exists()) {
			createDirectories(folder, true, true, new NullProgressMonitor());
		}
		IFile file = folder.getFile(entityShortName + ".java");		
		if (!file.exists()) {
			String content = "package " + packageName + ";\n\n" 
					+ "import javax.persistence.*;\n\n" 
					+ "@Entity \n"
					+ "public class " + entityShortName + " {\n"
					+ "	@Id \n"					
					+ "	private int id;\n"
					+ "	public int getId() {\n" 
					+ "		return id;\n"
					+ "	}\n"
					+ "	public void setId(int id) {\n"
					+ "		this.id = id;\n" 
					+ "	}\n"
					+ "}"; //$NON-NLS-1$
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

	public IFile createFieldAnnotatedEntityInProject(IProject project, String[] packageFragments, String entityName) throws IOException, CoreException, JavaModelException {
		String folderName = getFolderName(project, packageFragments);
		String packageName = packageFragments[0];		
		for (int i = 1; i < packageFragments.length; i++) {
			packageName += "." + packageFragments[i];
		}
		
		IPath path = new Path(folderName);
		IFolder folder = project.getFolder(path);		 
		return createFieldAnnotatedEntity(folder, packageName , entityName);
	}
	
	/**
	 * Wait all build and refresh jobs to complete by joining them.
	 */
	public static void joinBuildAndRerfreshJobs() {
		ArrayList<Job> jobs = new ArrayList<Job>();
		Job[] jobsArray;
		jobsArray = Job.getJobManager().find(ResourcesPlugin.FAMILY_AUTO_BUILD);
		jobs.addAll(Arrays.asList(jobsArray));
		jobsArray = Job.getJobManager().find(ResourcesPlugin.FAMILY_AUTO_REFRESH);
		jobs.addAll(Arrays.asList(jobsArray));
		jobsArray = Job.getJobManager().find(ResourcesPlugin.FAMILY_MANUAL_BUILD);
		jobs.addAll(Arrays.asList(jobsArray));
		jobsArray = Job.getJobManager().find(ResourcesPlugin.FAMILY_MANUAL_REFRESH);
		jobs.addAll(Arrays.asList(jobsArray));
		Utils.printlnFormatted("Waiting for " + jobs.size() + " Jobs to finish...");
		for (int i = 0; i < jobs.size(); i++) {
			Job job = jobs.get(i);
			int jobState = job.getState();
			if ((jobState == Job.RUNNING) || (jobState == Job.WAITING)) {
				try {
					job.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Wait all build and refresh jobs to complete or until 30 seconds timeout
	 * pass.
	 */
	public static void waitBuildAndRerfreshJobs() {
		Utils.printlnFormatted("Entered into joinBuildAndRerfreshJobs.");
		ArrayList<Job> jobs = new ArrayList<Job>();
		Job[] jobsArray;
		int timeout = SIDE_JOBS_COMPLETE_TIMEOUT;

		while (timeout > 0) {
			jobs.clear();
			jobsArray = Job.getJobManager().find(ResourcesPlugin.FAMILY_AUTO_BUILD);
			jobs.addAll(Arrays.asList(jobsArray));
			jobsArray = Job.getJobManager().find(ResourcesPlugin.FAMILY_AUTO_REFRESH);
			jobs.addAll(Arrays.asList(jobsArray));
			jobsArray = Job.getJobManager().find(ResourcesPlugin.FAMILY_MANUAL_BUILD);
			jobs.addAll(Arrays.asList(jobsArray));
			jobsArray = Job.getJobManager().find(ResourcesPlugin.FAMILY_MANUAL_REFRESH);
			jobs.addAll(Arrays.asList(jobsArray));
			if (jobs.size() == 0) {
				return;
			}
			Utils.printlnFormatted("Waiting for " + jobs.size() + " Jobs to finish...");
			for (Job job : jobsArray) {
				Utils.printlnFormatted("Waiting for job: " + job.getName() + ", which is " + jobStateToString(job));
			}
			timeout -= 1000;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

		}
	}
	
	private static String jobStateToString(Job job) {
		int state = job.getState();
		switch (state) {
			case Job.NONE:
				return "NONE";
			case Job.RUNNING:
				return "RUNNING";
			case Job.SLEEPING:
				return "SLEEPING";
			case Job.WAITING:
				return "SLEEPING";
		}
		return "UNKNOWN";
	}

	
	
	/**
	 * Waits a certain time for any non system job to complete and if there is
	 * still jobs after the certain periord,<br/>
	 * removes all of them so the test will have a clean environment
	 * 
	 * @return flag
	 * @throws InterruptedException
	 */
	public static boolean waitNonSystemJobs() throws InterruptedException {
		Utils.printlnFormatted("Entered into waitNonSystemJobs.");
		return waitNonSystemJobs(SIDE_JOBS_COMPLETE_TIMEOUT, true);
	}

	/**
	 * Waits a certain time for any non system job to complete and if there is still jobs after the certain periord,<br/>
	 * removes all of them so the test will have a clean environment
	 * 
	 * @param timeout
	 *            time after which the jobs will be canceled.
	 * @return
	 * @throws InterruptedException
	 */
	public static boolean waitNonSystemJobs(int timeout) throws InterruptedException {
		return waitNonSystemJobs(timeout, true);
	}

	/**
	 * Waits a certain time for any non system job to complete and if there is
	 * still jobs after the certain periord,<br/>
	 * removes all of them so the test will have a clean environment
	 * 
	 * @param timeout
	 *            time after which the jobs will be canceled.
	 * @param cancelWaitForBrowser
	 *            will cancel rightaway the wait for browser jobs
	 * @return
	 * @throws InterruptedException
	 */
	public static boolean waitNonSystemJobs(int timeout, boolean cancelWaitForBrowser) throws InterruptedException {
		Job[] find = null;
		int count = 0;

		while (timeout > 0) {
			count = 0;
			find = Job.getJobManager().find(null);
			for (Job job : find) {
				if (!job.isSystem()) {
					count++;
					if (!DUMPER_JOB_NAME.equals(job.getName())) {
						Utils.printlnFormatted("Waiting for job: " + job.getName() + ", which is " + jobStateToString(job));
					}
				}
			}
			if (DEBUG && (count == 1)) {
				return true;
			} else if (!DEBUG && (count == 0)) {
				return true;
			}
			timeout -= 1000;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

		}
		Utils.println("Cancelling any running job...");

		find = Job.getJobManager().find(null);
		for (Job job : find) {
			if (!job.isSystem() && !DUMPER_JOB_NAME.equals(job.getName())) {
				printTrace(job);
				cancelJob(job);
			}
		}

		find = Job.getJobManager().find(null);
		count = 0;
		for (Job job : find) {
			if (!job.isSystem()) {
				count++;
			}
		}
		if (count == 0) {
			Utils.printlnFormatted("All jobs removed");
			return true;
		}

		Utils.printlnFormatted("There are still unremoved job. This may cause the test to block.");
		return false;
	}
	
	/**
	 * Cancels all running system jobs.
	 * 
	 * @return true if no jobs are running at the end.
	 * @throws InterruptedException
	 */
	public static boolean cancelAllNonSystemJobs() throws InterruptedException {
		waitNonSystemJobs(0);
		return waitNonSystemJobs();
	}

	private static void printTrace(Job job) {
		Thread thread = job.getThread();
		if (thread == null) {
			Utils.println("Null Thread ....");
			return;
		}
		StackTraceElement[] stackTrace = thread.getStackTrace();
		if (stackTrace != null) {
			for (StackTraceElement traceElement : stackTrace) {
				if (traceElement != null) {
					Utils.println("\tat " + traceElement.toString());
				}
			}
		}
	}

	/**
	 * Will call cancel job and produce a log
	 * 
	 * @param job
	 */
	private static void cancelJob(Job job) {
		Utils.printlnFormatted("* [" + job.getName() + "]...");
		job.cancel();
		Utils.printlnFormatted("* [" + job.getName() + "]...cancelled.");
	}

	
	// used to decide whether to print Job Manager's state
		private static boolean DEBUG = false;

		// seconds
		private static final String DUMPER_JOB_NAME = "Dumper 1";
		
		private static final int SIDE_JOBS_COMPLETE_TIMEOUT = 30 * 1000; // 30


}
