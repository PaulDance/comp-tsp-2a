.data 0x10010000					# data segment, at 0x1001 0000
.space 256							# char counters array
.ascii "Avec ses quatre dromadaires\n"
.ascii "Don Pedro d'Alfaroubeira\n"
.ascii "Courut le monde et l'admira.\n"
.ascii "Il fit ce que je voudrais faire\n"
.ascii "Si j'avais quatre dromadaires.\n"
.byte 0								# string ending zero


.text
		lui		$s0, 0x1001			# load address of data segment
		addiu	$s1, $s0, 256		# $s1 = address of the string
		addiu	$s2, $s0, 0			# $s2 = address of the array
		
		move	$a0, $s1
		move	$a1, $s2
		jal		compute_frequencies

	return:
		addiu	$v0, $zero, 10		# exit program
		syscall


	compute_frequencies:
		subiu	$sp, $sp, 12		# reserve 12 bytes on the stack
		sw		$ra, 0($sp)			# save $ra in $sp + 0
		sw		$s1, 4($sp)			# save $s1 in $sp + 4
		sw		$s2, 8($sp)			# save $s2 in $sp + 8
		move	$s1, $a0
		move	$s2, $a1
	lcf:
		lbu		$a0, ($s1)			# load current character
		beq		$a0, $zero, alcf	# end loop if zero
		
		li		$v0, 11				# print char
		syscall
		
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
		addiu	$sp, $sp, 12		# free the 12 bytes.
		jr		$ra
