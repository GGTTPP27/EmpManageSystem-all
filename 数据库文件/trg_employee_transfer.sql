CREATE DEFINER=`root`@`localhost` TRIGGER `trg_employee_transfer` AFTER UPDATE ON `employee` FOR EACH ROW BEGIN
    -- 当部门更换 或 仅在本部门更换职务时 触发调动记录插入
    IF old.dpt_id <> NEW.dpt_id or old.emp_job <> NEW.emp_job or OLD.emp_title <> NEW.emp_title THEN
        INSERT INTO transfer (
            emp_id,
            transfer_date,    
            transfer_out_dpt,
            transfer_in_dpt,
            transfer_reason
        ) VALUES (
            old.emp_id,
            CURDATE(),           
            OLD.dpt_id,
            NEW.dpt_id,
            NEW.transfer_reason_temp
        );
    END IF;
END

