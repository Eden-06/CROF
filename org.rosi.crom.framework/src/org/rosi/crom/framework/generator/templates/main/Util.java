/**
 * 
 */
package org.rosi.crom.framework.generator.templates.main;

import java.util.Set;
import java.io.File;
import java.util.HashSet;
import org.eclipse.emf.common.util.URI;
import org.rosi.crom.framework.ui.wizard.newCROMProjectWizard;

import crom_l1_composed.RoleType;
import crom_l1_composed.impl.RoleTypeImpl;

/**
 * This is a simple utility class to store and retrieve the modelURI of the
 * currently processed Ecore model instance (xmi). Most importantly it defines a
 * getModelUri() method that emits a corresponding string representation.
 * 
 * @author eden06
 *
 */
public class Util {

	public static URI modeluri = null;
	public static File filePath = null;
	public static String folderPath = "";
	public static String folderName = "";
	public static String packagePath = "";
	public static Set<RoleType> thisset = new HashSet<RoleType>();

	public Util() {
	}

	/**
	 * Set the modelUri as static variable accessed via the Util objects.
	 * 
	 * @param m
	 *            the modelURI to be set
	 */
	public static void setModelUri(URI m) {
		modeluri = m;
	}

	/**
	 * Returns the last modelURI that has been set globally, via either of the Util
	 * objects, or "model", if static variable is undefined (or set to null).
	 * 
	 * @return a string representation of the modelURI or simply the String "model"
	 *         if no modeluri was set.
	 */
	public String getModelUri() {
		return modeluri.toString();
	}

	public static void setFilePath(File m) {
		filePath = m;
	}

	/**
	 * Returns the last modelURI that has been set globally, via either of the Util
	 * objects, or "model", if static variable is undefined (or set to null).
	 * 
	 * @return a string representation of the modelURI or simply the String "model"
	 *         if no modeluri was set.
	 */
	public String getFilePath() {
		return filePath.toString();
	}

	public static void setPackagePath(String str) {
		packagePath = str;
	}

	public String getPackagePath() {
		return packagePath;
	}

	public static void setFolderPath(String str) {
		folderPath = str;
	}

	public String getFolderPath() {
		return folderPath;
	}

	public static void setFolderName(String str) {
		folderName = str;
	}

	public String getFolderName() {
		return folderName;
	}

	public boolean setRole(RoleTypeImpl role) {
		return thisset.add((RoleType) role);
	}

	public Set<RoleType> getRole() {
		return thisset;
	}

}
