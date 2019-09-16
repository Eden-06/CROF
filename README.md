# Contextual Relational Role-based Modeling Framework

This Repository contains the proof of concept "Contextual and Relational Role-based Modeling Framework" based on the [Compartment Role Object Model (CROM)](https://github.com/Eden-06/CROM) and the [Fulfledged Role Modeling Editor 2.0 (FRaMED)](https://github.com/Eden-06/FRaMED-2.0). 

### Requirements
* Tested with Eclipse Photon Modeling Tools
* Acceleo 3.7
* [FRaMED 2.0](https://github.com/Eden-06/FRaMED-2.0)
* or alternatively only [CROM](https://github.com/Eden-06/CROM)

### How to import the CROM Framework into Eclipse
1. Install *FRaMED 2.0* using the [Step-by-Step installation guied](https://github.com/Eden-06/FRaMED-2.0/wiki/Install)
2. Install the following packages via `Help -> Eclipse Marketplace`
    * Install *Acceleo 3.7* (or higher)
        * Search for `Acceleo`
        * Select it, click on the *"Install"* link, and complete the installation
3. Import the *CROF* repository into Eclipse.
    1. Select "File" -> "Import" to open the import wizard, select "Projects from Git" and hit "Next>".
    2. Enter <https://github.com/Eden-06/CROF.git> into the URI field and hit "Next>"
    3. Only select the **master** branch and continue with "Next>"
    4. Continue with "Next>" until you can "Finish" the project import.
4. Start a new **Eclipse** instance to run the plugin:
   * Select `org.rosi.crom.metamodel`, `Run As -> Eclipse Application`
   * If you get a validation error `org.apache.xmlrpc` just continue

After installing and running the plugin, a new Eclipse Instance is opened.

### Case Study

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
    2. Find the `transfer` method and replace the method implementation by the following code.

        ```{Java}
        @Override
        public void transfer(int sourceId, Account target, double amount){
          if (amount<=0) throw new IllegalArgumentException();
          //find source account by id
          java.util.Optional<Account> as=java.util.stream.Stream
            .concat(accountSavingsAccounts.stream(), accountCheckingAccounts.stream())
            .map(a -> (Account) a).filter(a -> a.getId()==sourceId).findFirst();
          Account source=as.orElse(null);
          if (source!=null && target!=null && (!source.isSame(target))){
            //Create a Transaction compartment
            Transaction t=getModel().createTransaction();
            t.bindSource(source);
            t.bindTarget(target);
            t.setAmount(amount);
            t.setCreationTime(java.time.Instant.now().getNano());
            this.bindMoneyTransfer(t);
            System.out.println("Transaction created: "+t.toString());
          }else {
            System.out.println("Transaction failed to create for sourceId="+sourceId);
          }
        }
        ```

8. Finally, the `Main.java` can be run as plain Java Application via the context menu "Run > Java Application".
9. After a successful run, two model instances are persisted as json files, i.e., `bank.croj` and `instances/model.croj`. These can be inspected as needed.

After setting everything up, you can further explore the CROM framework by modifying the `Main.java` to create different model instances, or modifying the `bank.crom` file and regenerate the model code to add new naturals, roles, and compartments to the model.


