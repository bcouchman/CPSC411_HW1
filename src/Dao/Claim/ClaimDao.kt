package org.csuf.cspc411.Dao.Claim

import org.csuf.cspc411.Dao.Dao
import org.csuf.cspc411.Dao.Database
import java.util.*

class ClaimDao : Dao() {

    fun addClaim(dObj : Claim) {
        // 1. Get db connection
        val conn = Database.getInstance()?.getDbConnection()

        // 2. prepare the sql statement
        sqlStmt = "insert into claim (title, date, id, isSolved) values ('${dObj.title}', '${dObj.date}', '${dObj.id}', '${dObj.isSolved}')"

        // 3. submit the sql statement
        conn?.exec(sqlStmt)
    }

    fun getAll() : List<Claim> {
        // 1. Get db connection
        val conn = Database.getInstance()?.getDbConnection()

        // 2. prepare the sql statement
        sqlStmt = "select title, date, id, isSolved from claim"

        // 3. submit the sql statement
        var claimList : MutableList<Claim> = mutableListOf()
        val st = conn?.prepare(sqlStmt)

        // 4. Convert into Kotlin object format
        while (st?.step()!!) {
            val title = st.columnString(0)
            val date = st.columnString(1)
            val id = UUID.fromString(st.columnString(2))
            val isSolved = st.columnString(3).toBoolean()
            claimList.add(Claim(title, date, id, isSolved))
        }
        return claimList
    }
}