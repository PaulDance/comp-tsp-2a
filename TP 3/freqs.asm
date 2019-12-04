.data 0x10010000					# data segment, at 0x1001 0000
	.space 256						# char counters array
	
	.ascii " => "
	.byte 0
	
	.ascii "\n"
	.byte 0
	
	.ascii "Avec ses quatre dromadaires\n"
	.ascii "Don Pedro d'Alfaroubeira\n"
	.ascii "Courut le monde et l'admira.\n"
	.ascii "Il fit ce que je voudrais faire\n"
	.ascii "Si j'avais quatre dromadaires.\n"
	.byte 0							# string ending zero


.text
		lui		$s0, 0x1001			# load address of data segment
		addiu	$s1, $s0, 263		# $s1 = address of the string
		addiu	$s2, $s0, 0			# $s2 = address of the array
		
		move	$a0, $s1
		move	$a1, $s2
		jal		computeFrequencies
		
		jal		printFrequencies
		
	exit:
		li		$v0, 10				# exit program
		syscall


	computeFrequencies:
		subiu	$sp, $sp, 12		# reserve 12 bytes on the stack
		sw		$ra, 0($sp)			# save $ra in $sp + 0
		sw		$s1, 4($sp)			# save $s1 in $sp + 4
		sw		$s2, 8($sp)			# save $s2 in $sp + 8
		move	$s1, $a0
		move	$s2, $a1
	
	lcf:
		lbu		$a0, ($s1)			# load current character
		beqz	$a0, alcf			# end loop if zero
		
		#li		$v0, 11				# print char
		#syscall
		
		addu	$t0, $s2, $a0
		lbu		$t1, ($t0)
		addiu	$t1, $t1, 1
		sb		$t1, ($t0)
		
		addi	$s1, $s1, 1			# $s1 = $s1 + 1
		j		lcf					# loop
		
	alcf:
		lw		$ra, 0($sp)			# restore saved values
		lw		$s1, 4($sp)
		lw		$s2, 8($sp)
		addiu	$sp, $sp, 12		# "free" the 12 bytes.
		jr		$ra


	printlnFrequency:
		subiu	$sp, $sp, 4			# Function enter.
		sw		$ra, ($sp)
		
		li		$v0, 11				# Display $a0 (char)
		syscall
		
		addiu	$a0, $s0, 256
		li		$v0, 4				# Display " => "
		syscall
		
		move	$a0, $a1
		li		$v0, 1				# Display $a1 (freq)
		syscall
		
		addiu	$a0, $s0, 261
		li		$v0, 4				# Display "\n"
		syscall
		
		lw		$ra, 0($sp)
		addiu	$sp, $sp, 4
		jr		$ra
	
	
	printFrequencies:
		subiu	$sp, $sp, 4			# Function enter.
		sw		$ra, ($sp)
		li		$t0, 1				# Character iterator
		
	pl:
		move	$a0, $t0			# Load character
		addu	$t1, $s2, $t0
		lbu		$a1, ($t1)			# and frequency.
		
		beqz	$a1, plNext			# If freq == 0, go to next char,
		
		jal		printlnFrequency	# else display char and freq.
	
	plNext:
		addiu	$t0, $t0, 1			# Go to the next char,
		bne		$t0, 255, pl		# loop if < 256 (ASCII).
		
		lw		$ra, ($sp)
		addiu	$sp, $sp, 4
		jr		$ra					# Leave, no value.
