# Contextual Relational Role-based Modeling Framework

This Repository contains the proof of concept "Contextual and Relational Role-based Modeling Framework" based on the [Compartment Role Object Model (CROM)](https://github.com/Eden-06/CROM) and the [Fulfledged Role Modeling Editor 2.0 (FRaMED)](https://github.com/Eden-06/FRaMED-2.0). 

# Prerequisits

* Eclipse Photon Modeling Tools
* Acceleo 2.6
* CROM Metamodel
* Java 1.8

# Installation

1. Download, install, and start "Eclipse Modeling Tools" (version Photon) [\[here\]](https://www.eclipse.org/downloads/packages/release/photon/r/eclipse-modeling-tools).
2. Install the following packages via `Help -> Eclipse Marketplace`
    * Install *Acceleo 2.6*
        * Search for `acceleo`
        * Select it, click on the *"Install"* link, and complete the installation   
        * (Installing *Eugenia* is not required, so it can be discarded)
    * Install *Xtext 2.11.0*
        * Search for `xtext`
        * Select it, click on the *"Install"* link, and complete the installation
    * Install *Xtend 2.11.0*
        * Search for `xtend`
        * Select it, click on the *"Install"* link, and complete the installation  
2. Import *CROM* repository via <https://github.com/Eden-06/CROM.git> in the same way.
    * Open the `src-gen/crom_l1_composed.genmodel` in the GenModel Editor 
    * `Reload...` the model via the context menu (using the `model/crom_l1_composed.ecore` file)
    * `Generate all` classes via the context menu
3. Import the *CROF* repository into Eclipse.
    1. Select "File" -> "Import" to open the import wizard, select "Projects from Git" and hit "Next>".
    2. Enter <https://github.com/Eden-06/CROF.git> into the URI field and hit "Next>"
    3. Only select the **master** branch and continue with "Next>"
    4. Continue with "Next>" until you can "Finish" the project import.
4. Start a new **Eclipse** instance to run the plugin:
   * Select `org.rosi.crom.metamodel`, `Run As -> Eclipse Application`
   * If you get a validation error `org.apache.xmlrpc` just continue
5. Optionally, you could install the graphical *FRaMED 2.0* editor following the instructions [\[here\]](https://github.com/Eden-06/FRaMED-2.0/wiki/Install).

After installing and running the plugin, a new Eclipse Instance is opened.

# Case Study

1. Import the *CROF-Example* repository into the Eclipse Instance.
    1. Select "File" -> "Import" to open the import wizard, select "Projects from Git" and hit "Next>".
    2. Enter <https://github.com/Eden-06/CROF-Example.git> into the URI field and hit "Next>"
    3. Only select the **master** branch and continue with "Next>"
    4. Continue with "Next>" until you can "Finish" the project import.
2. Open the added *Bank* project and open the model folder.
    * If *FRaMED 2.0* was installed, simply open the `bank.crom_diagram` file by double clicking on it.
    * Otherwise, open the `bank.crom` file directly (double click) in the standard Tree Editor.
3. By left clicking on the `bank.crom` file, and clicking on the context menu entry "Generate > CROM Framework", the framework is tasked to create the model code for the *Bank* CROM model.
4. Inspect the generated Code by looking into the `src/` folder.
5. Open the `Main.java` file in the `src/bank/` directory.
6. Uncomment the source code to programmatically create, store and load a model instance.
7. Because the implementation of the `Bank.transfer` is still empty, the model cannot be run yet.
    1. Open the `BankImpl.java` in the `src/impl` folder.
    2. Find the `transfer` method and repalce the method implementation by the following code.

        ```{Java}
        @Override
        public void transfer(int sourceId, Account target, double amount){
          if (amount<=0) throw new IllegalArgumentException();
          //find source account by id
          Optional<Account> as=Stream
				        .concat(accountSavingsAccounts.stream(), accountCheckingAccounts.stream())
				        .map(a -> (Account) a)
				        .filter(a -> a.getId()==id)
				        .findFirst();
          Account source=as.orElse(null);
          if (source!=null && target!=null && (!source.isSame(target))){
            //Create a Transaction compartment
            Transaction t=getModel().createTransaction();
            t.bindSource(source);
            t.bindTarget(target);
            t.setAmount(amount);
            t.setCreationTime(Instant.now().getNano());
            this.bindMoneyTransfer(t);
            System.out.println("Transaction created: "+t.toString());
          }else {
            System.out.println("Transaction failed to create for sourceId="+sourceId);
          }
        }
        ```

8. Finally, the `Main.java` can be run as plain Java Application via the context menu "Run > Java Application".

