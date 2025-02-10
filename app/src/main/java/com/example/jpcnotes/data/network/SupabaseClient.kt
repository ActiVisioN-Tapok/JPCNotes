
package com.example.jpcnotes.data.network


import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = "https://nxwxjcymbiblgnglnfwy.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im54d3hqY3ltYmlibGduZ2xuZnd5Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzY3NDE4MzcsImV4cCI6MjA1MjMxNzgzN30.Kt9edX5rJXn59z9xPacukrX_G6LX2D1f05QdJD99UW0")
    {
        install(Auth)
        install(Postgrest)
    }
}
