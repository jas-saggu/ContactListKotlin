//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
enum class ContactType {
    PERSONAL,
    BUSINESS,
    FAMILY
}

data class Contact(
    var firstName: String,
    var lastName: String,
    var midName: String?,
    var website: String?,
    var phoneNumber: String,
    var email: String?,
    var contactType: ContactType
)

class ContactManager( val contacts: MutableList<Contact> = mutableListOf() ) {

    fun addContact(contact: Contact) {
        contacts.add(contact)
    }
    fun removeContact(phoneNumber: String) {

        if (contacts.isEmpty()) {
            println("No contacts available to remove.")
            return
        }

        val removed = contacts.removeIf { it.phoneNumber == phoneNumber }

        if (removed) {
            println("Contact with phone number $phoneNumber removed successfully.")
        } else {
            println("No contact found with phone number $phoneNumber.")
        }
    }

    fun listContacts() {
        println("===== Contact List =====\n")

        if (contacts.isEmpty()) {
            println("No contacts available.")
            return
        }

        contacts.forEachIndexed { index, contact ->
            println("Contact #${index + 1}")
            println("First Name: ${contact.firstName}")
            println("Last Name: ${contact.lastName}")
            contact.midName?.let { println("Middle Name: $it") } ?: println("Middle Name: N/A")
            contact.website?.let { println("Website: $it") } ?: println("Website: N/A")
            println("Phone Number: ${contact.phoneNumber}")
            contact.email?.let { println("Email: $it") } ?: println("Email: N/A")

            println("Contact Type: ${contact.contactType}")
            println("-----------------------------")
        }
    }

    fun searchContactsByFirstName(name: String) {
        val results = contacts.filter {
            it.firstName.equals(name, ignoreCase = true)
        }

        if (results.isEmpty()) {
            println("No contacts found with first name \"$name\".")
        } else {
            println("===== Search Results =====")
            results.forEachIndexed { index, contact ->
                println("Contact #${index + 1}")
                println("First Name: ${contact.firstName}")
                println("Last Name: ${contact.lastName}")
                println("Middle Name: ${contact.midName ?: "N/A"}")
                println("Website: ${contact.website ?: "N/A"}")
                println("Phone Number: ${contact.phoneNumber}")
                println("Email: ${contact.email ?: "N/A"}")
                println("Contact Type: ${contact.contactType}")
                println("-----------------------------")
            }
        }
    }


}


fun main() {
    val manager = ContactManager()
    var running = true

    while (running) {
        println("===== Contact Book Menu =====")
        println("1. Add Contact")
        println("2. Remove Contact")
        println("3. List Contacts")
        println("4. Search Contacts")
        println("5. Exit")
        print("Enter your choice: ")

        when (readLine()?.trim()) {
            "1" -> {
                // Mandatory fields with validation
                var firstName: String?
                do {
                    print("First Name (mandatory): ")
                    firstName = readLine()?.trim()
                } while (firstName.isNullOrEmpty())

                var lastName: String?
                do {
                    print("Last Name (mandatory): ")
                    lastName = readLine()?.trim()
                } while (lastName.isNullOrEmpty())

                var phoneNumber: String?
                do {
                    print("Phone Number (mandatory): ")
                    phoneNumber = readLine()?.trim()
                } while (phoneNumber.isNullOrEmpty())

                // Optional fields
                print("Middle Name (optional): ")
                val midName = readLine()?.trim().takeIf { !it.isNullOrEmpty() }

                print("Website (optional): ")
                val website = readLine()?.trim().takeIf { !it.isNullOrEmpty() }

                print("Email (optional): ")
                val email = readLine()?.trim().takeIf { !it.isNullOrEmpty() }

                // Contact type
                print("Contact Type (PERSONAL/BUSINESS/FAMILY): ")
                val typeInput = readLine()?.trim()?.uppercase()
                val contactType = ContactType.values().find { it.name == typeInput } ?: ContactType.PERSONAL

                val contact = Contact(firstName, lastName, midName, website, phoneNumber, email, contactType)
                manager.addContact(contact)
            }
            "2" -> {
                print("Enter Phone Number of contact to remove: ")
                val phone = readLine()?.trim()
                if (!phone.isNullOrEmpty()) {
                    manager.removeContact(phone)
                } else {
                    println("Invalid input. Phone number is required to remove a contact.")
                }
            }
            "3" -> manager.listContacts()
            "4" -> {
                print("Enter First Name to search: ")
                val name = readLine()?.trim()
                if (!name.isNullOrEmpty()) {
                    manager.searchContactsByFirstName(name)
                } else {
                    println("Invalid input. First name is required to search.")
                }
            }
            "5" -> {
                println("Exiting...")
                running = false
            }
            else -> println("Invalid choice, please try again.")
        }

        println()  // blank line for readability
    }
}
